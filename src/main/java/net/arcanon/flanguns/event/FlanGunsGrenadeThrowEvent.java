package net.arcanon.flanguns.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class FlanGunsGrenadeThrowEvent extends Event {

    private Player player;
    private ItemStack itemStack;
    private int slot;

    public FlanGunsGrenadeThrowEvent(Player player, ItemStack itemStack, int slot) {
        this.player = player;
        this.itemStack = itemStack;
        this.slot = slot;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getSlot() {
        return slot;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}