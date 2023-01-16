package net.arcanon.flanguns.listener;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.FlanGuns;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerDropListener implements Listener {

    private final FlanGuns plugin;

    public PlayerDropListener(FlanGuns plugin) {
        this.plugin = plugin;
    }

    // Haal de speler uit zoom als hij een item dropped terwijl hij nog in zoom zit
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItemDrop().getItemStack();
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            return;
        }

        ItemMeta handMeta = itemStack.getItemMeta();
        NBTItem nbtItem = new NBTItem(itemStack);

        // Item in hand is not a gun
        if (!nbtItem.hasKey("FlanGuns_maxAmmo")) {
            return;
        }

        if (plugin.getZoomManager().isInZoom(player)) {
            e.setCancelled(true);
        }
    }
}