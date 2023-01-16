package net.arcanon.flanguns.listener;

import net.arcanon.flanguns.FlanGuns;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private final FlanGuns plugin;

    public InventoryClickListener(FlanGuns plugin) {
        this.plugin = plugin;
    }

    // Cancel klikken op off-hand slot in Inventory
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getSlot() == 40) {
            e.setCancelled(true);
        }
    }
}