package net.arcanon.flanguns.listener;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.FlanGuns;
import net.arcanon.flanguns.gun.AmmoManager;
import net.arcanon.flanguns.item.ItemType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class SlotSwitchListener implements Listener {

    private final FlanGuns plugin;
    private static Map<UUID, BukkitTask> schedulerMap;
    private static List<UUID> inMinigunSlow;

    public SlotSwitchListener(FlanGuns plugin) {
        this.plugin = plugin;
        this.schedulerMap = new HashMap<>();
        this.inMinigunSlow = new ArrayList<>();
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onHotbar(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        Inventory inventory = player.getInventory();

        ItemStack itemStack = inventory.getItem(e.getNewSlot());

        if (plugin.getZoomManager().isInZoom(player)) {
            plugin.getZoomManager().setInZoom(player, false);
            player.removePotionEffect(PotionEffectType.SLOW);
            player.getInventory().setItemInOffHand(null);
        }

        if (inMinigunSlow.contains(player.getUniqueId())) {
            player.removePotionEffect(PotionEffectType.SLOW);
            inMinigunSlow.remove(player.getUniqueId());
        }

        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            if (schedulerMap.containsKey(player.getUniqueId())) {
                schedulerMap.get(player.getUniqueId()).cancel();
                schedulerMap.remove(player.getUniqueId());
            }
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
            return;
        }
        NBTItem nbtItem = new NBTItem(itemStack);

        if (!nbtItem.hasNBTData() || !nbtItem.hasKey("FlanGuns_type") || !nbtItem.getString("FlanGuns_type").equals(ItemType.GUN.name())) {
            if (schedulerMap.containsKey(player.getUniqueId())) {
                schedulerMap.get(player.getUniqueId()).cancel();
                schedulerMap.remove(player.getUniqueId());
            }
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
            return;
        }

        UUID uuid = UUID.fromString(nbtItem.getString("FlanGuns_gunUUID"));
        AmmoManager ammoManager = plugin.getAmmoManager();

        // Register gun if needed
        if (!ammoManager.isRegistered(uuid)) {
            ammoManager.register(uuid, (short) 0);
        }

        // Minigun slow effect
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().equals("Minigun")) {
            inMinigunSlow.add(player.getUniqueId());
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
        }

        // Ammo show text loop
        BukkitTask runnable = new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack itemStack = player.getItemInHand();
                if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
                    this.cancel();
                } else {
                    NBTItem nbtItem = new NBTItem(itemStack);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§f" + ammoManager.getAmmo(uuid) + "§7 / §f" + nbtItem.getShort("FlanGuns_maxAmmo")));
                }
            }
        }.runTaskTimerAsynchronously(plugin,0L,1L);
        if (schedulerMap.containsKey(player.getUniqueId())) {
            schedulerMap.get(player.getUniqueId()).cancel();
            schedulerMap.remove(player.getUniqueId());
        }
        schedulerMap.put(player.getUniqueId(), runnable);
    }
}