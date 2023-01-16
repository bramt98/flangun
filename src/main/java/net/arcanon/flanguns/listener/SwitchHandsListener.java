package net.arcanon.flanguns.listener;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.FlanGuns;
import net.arcanon.flanguns.ammo.Ammo;
import net.arcanon.flanguns.gun.AmmoManager;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.UUID;

public class SwitchHandsListener implements Listener {

    private final FlanGuns plugin;

    public SwitchHandsListener(FlanGuns plugin) {
        this.plugin = plugin;
    }

    // Runned wanneer je op 'f' klikt
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onHandSwitch(PlayerSwapHandItemsEvent e) {
        e.setCancelled(true);
        Player player = e.getPlayer();

        if (player.getInventory().getItemInMainHand() != null && !player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            NBTItem nbtItem = new NBTItem(itemStack);
            UUID uuid = UUID.fromString(nbtItem.getString("FlanGuns_gunUUID"));
            AmmoManager ammoManager = plugin.getAmmoManager();

            Gun gun = plugin.getItemManager().getGun(itemStack, uuid);
            if (gun == null) {
                return;
            }
            Ammo requiredAmmo = gun.getAmmoType();
            Ammo fireAmmo = gun.getFireType();
            Ammo explosiveAmmo = gun.getExplosiveType();
            Ammo poisonAmmo = gun.getPoisonType();

            // Register gun if needed
            if (!ammoManager.isRegistered(uuid)) {
                ammoManager.register(uuid, (short) 0);
            }

            // Needs to reload
            if (ammoManager.getAmmo(uuid) < requiredAmmo.getRoundsPerItem()) {

                ItemStack ammo = null;
                boolean hasAmmo = false;
                for (ItemStack invContent : player.getInventory().getContents()) {
                    if (invContent != null && !invContent.getType().equals(Material.AIR) && invContent.hasItemMeta() && invContent.getItemMeta().hasDisplayName()) {
                        String newName = requiredAmmo.getName().replace("_", " ");
                        String newFireName = "";
                        if (fireAmmo != null) {
                            newFireName = fireAmmo.getName().replace("_", " ");
                        }
                        String newExplosiveName = "";
                        if (explosiveAmmo != null) {
                            newExplosiveName = explosiveAmmo.getName().replace("_", " ");
                        }
                        String newPoisonName = "";
                        if (poisonAmmo != null) {
                            newPoisonName = poisonAmmo.getName().replace("_", " ");
                        }

                        String displayName = invContent.getItemMeta().getDisplayName();
                        if (displayName.equals(newName)) {
                            hasAmmo = true;
                            nbtItem.setString("FlanGuns_bulletType", "REGULAR");
                            player.getInventory().setItemInMainHand(nbtItem.getItem());

                            ammo = invContent;
                            break;
                        } else if (displayName.equals(newFireName)) {
                            hasAmmo = true;
                            nbtItem.setString("FlanGuns_bulletType", "FIRE");
                            player.getInventory().setItemInMainHand(nbtItem.getItem());

                            ammo = invContent;
                            break;
                        } else if (displayName.equals(newExplosiveName)) {
                            hasAmmo = true;
                            nbtItem.setString("FlanGuns_bulletType", "EXPLOSIVE");
                            player.getInventory().setItemInMainHand(nbtItem.getItem());

                            ammo = invContent;
                            break;
                        } else if (displayName.equals(newPoisonName)) {
                            hasAmmo = true;
                            nbtItem.setString("FlanGuns_bulletType", "POISON");
                            player.getInventory().setItemInMainHand(nbtItem.getItem());

                            ammo = invContent;
                            break;
                        }
                    }
                }

                if (hasAmmo) {
                    if (plugin.getReloadManager().isInReload(player)) {
                        return;
                    }

                    if (plugin.getZoomManager().isInZoom(player)) {
                        plugin.getZoomManager().setInZoom(player, false);
                        player.removePotionEffect(PotionEffectType.SLOW);
                        player.getInventory().setItemInOffHand(null);
                    }

                    plugin.getReloadManager().setInReload(player, true);
                    player.getLocation().getWorld().playSound(player.getLocation(), gun.getReloadSound(), 2f, 1f);

                    NBTItem nbtAmmo = new NBTItem(ammo);

                    // Geen ammo over in clip
                    if (nbtAmmo.getShort("FlanGuns_currentRounds") - (gun.getAmmoType().getRoundsPerItem() - ammoManager.getAmmo(uuid)) <= 0) {

                        ammo.setAmount(ammo.getAmount() - 1);
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            ammoManager.setAmmo(uuid, (short) (ammoManager.getAmmo(uuid) + nbtAmmo.getShort("FlanGuns_currentRounds")));
                            plugin.getReloadManager().setInReload(player, false);
                        }, gun.getReloadTime());
                    }

                    // Wel ammo over in clip
                    else {
                        ItemStack ammoClone1 = ammo.clone();
                        NBTItem newNbtAmmo = new NBTItem(ammoClone1);
                        newNbtAmmo.setShort("FlanGuns_currentRounds", (short) (nbtAmmo.getShort("FlanGuns_currentRounds") - (gun.getAmmoType().getRoundsPerItem() - ammoManager.getAmmo(uuid))));
                        ItemStack ammoClone = newNbtAmmo.getItem();
                        ItemMeta itemMeta = ammoClone.getItemMeta();
                        List<String> lore = itemMeta.getLore();
                        lore.set(0, "§7Rounds: " + (nbtAmmo.getShort("FlanGuns_currentRounds") - (gun.getAmmoType().getRoundsPerItem() - ammoManager.getAmmo(uuid))));
                        itemMeta.setLore(lore);
                        ammoClone.setItemMeta(itemMeta);
                        ammoClone.setAmount(1);

                        // Durability zetten
//                        ammoClone.setDurability((short) (gun.getAmmoType().getRoundsPerItem() / (newNbtAmmo.getShort("FlanGuns_currentRounds") / nbtAmmo.getShort("FlanGuns_roundsPerItem"))));

                        ammo.setAmount(ammo.getAmount() - 1);
                        player.getInventory().addItem(ammoClone);
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            ammoManager.setAmmo(uuid, gun.getAmmoType().getRoundsPerItem());
                            plugin.getReloadManager().setInReload(player, false);
                        }, gun.getReloadTime());
                    }
                } else {
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1f, 0.9f);
                    return;
                }
            }
        }
    }
}