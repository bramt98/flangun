package net.arcanon.flanguns.listener;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.event.FlanGunsMedicalEvent;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class MedicalUseListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onMedicalUse(FlanGunsMedicalEvent e) {
        Player player = e.getPlayer();

        if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) {
            return;
        }

        ItemStack itemStack = e.getItemStack();
        NBTItem nbtItem = new NBTItem(itemStack);

        int slot = e.getSlot();
        double healAmount = nbtItem.getDouble("FlanGuns_healAmount");

        int uses = nbtItem.getInteger("FlanGuns_uses");
        if (uses == 1) {
            ItemStack copy = itemStack.clone();
            copy.setAmount(itemStack.getAmount() - 1);
            player.getInventory().setItem(slot, copy);
        } else if (uses == 2) {
            nbtItem.setInteger("FlanGuns_uses", 1);
            ItemStack nbtStack = nbtItem.getItem();
            nbtStack.setDurability((short) (((double) 3/4) * ((double) itemStack.getType().getMaxDurability())));
            player.getInventory().setItem(slot, nbtStack);
        } else if (uses == 3) {
            nbtItem.setInteger("FlanGuns_uses", 2);
            ItemStack nbtStack = nbtItem.getItem();
            nbtStack.setDurability((short) (((double) 1/2) * ((double) itemStack.getType().getMaxDurability())));
            player.getInventory().setItem(slot, nbtStack);
        }

        double newHealth = player.getHealth() + healAmount;
        if (newHealth >= player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        } else {
            player.setHealth(newHealth);
        }

        player.getWorld().spawnParticle(Particle.HEART, player.getLocation().clone().add(-1, 1, -1), 1);
        player.getWorld().spawnParticle(Particle.HEART, player.getLocation().clone().add(0, 0.9, -0.7), 1);
        player.getWorld().spawnParticle(Particle.HEART, player.getLocation().clone().add(0, 1.2, -1), 1);
        player.getWorld().spawnParticle(Particle.HEART, player.getLocation().clone().add(-1, 0.7, 0), 1);
        player.getWorld().spawnParticle(Particle.HEART, player.getLocation().clone().add(1, 0.8, 0), 1);
        player.getWorld().spawnParticle(Particle.HEART, player.getLocation().clone().add(0, 0.9, 1), 1);
        player.getWorld().spawnParticle(Particle.HEART, player.getLocation().clone().add(1, 1.1, 1), 1);
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1f, 1f);
        player.sendMessage("§7You've used a §f" + itemStack.getItemMeta().getDisplayName() + " §7and healed §c" + healAmount/2 + "❤§7!");
    }
}