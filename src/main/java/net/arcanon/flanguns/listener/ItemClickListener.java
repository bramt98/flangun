package net.arcanon.flanguns.listener;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.event.*;
import net.arcanon.flanguns.item.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemClickListener implements Listener {

    // Event triggers voor schieten, explosive gooien etc.
    @EventHandler
    public void onItemClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        ItemStack itemStack = e.getItem();
        if (itemStack == null) {
            return;
        }

        NBTItem nbtItem = new NBTItem(itemStack);

        if (!nbtItem.hasNBTData() || !nbtItem.hasKey("FlanGuns_type")) {
            return;
        }

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {

            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && itemStack.getType().equals(Material.DIAMOND_HOE)) {
                e.setCancelled(true);
            }

            if (nbtItem.getString("FlanGuns_type").equals(ItemType.REMOTE.name())) {
                Bukkit.getServer().getPluginManager().callEvent(new FlanGunsC4RemoteEvent(player));
            } else if (nbtItem.getString("FlanGuns_type").equals(ItemType.MEDICAL.name())) {
                Bukkit.getServer().getPluginManager().callEvent(new FlanGunsMedicalEvent(player, itemStack, player.getInventory().getHeldItemSlot()));
            } else if (nbtItem.getString("FlanGuns_type").equals(ItemType.GUN.name())) {
                Bukkit.getServer().getPluginManager().callEvent(new FlanGunsShootEvent(player, itemStack, player.getInventory().getHeldItemSlot()));
            } else if (nbtItem.getString("FlanGuns_type").equals(ItemType.EXPLOSIVE.name())) {
                Bukkit.getServer().getPluginManager().callEvent(new FlanGunsGrenadeThrowEvent(player, itemStack, player.getInventory().getHeldItemSlot()));
            }
        } else if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

            if (nbtItem.getString("FlanGuns_type").equals(ItemType.GUN.name())) {
                Bukkit.getServer().getPluginManager().callEvent(new FlanGunsZoomEvent(player, itemStack, player.getInventory().getHeldItemSlot()));
                e.setCancelled(true);
            }
        }
    }
}