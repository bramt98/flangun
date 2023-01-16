package net.arcanon.flanguns.listener;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.FlanGuns;
import net.arcanon.flanguns.event.FlanGunsGrenadeThrowEvent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class GrenadeThrowListener implements Listener {

    private final FlanGuns plugin;

    public GrenadeThrowListener(FlanGuns plugin) {
        this.plugin = plugin;
    }

    // Runned bij throwen van een Explosive item
    @EventHandler
    public void onGrenadeThrow(FlanGunsGrenadeThrowEvent e) {
        ItemStack itemStack = e.getItemStack();
        Player player = e.getPlayer();
        NBTItem nbtItem = new NBTItem(itemStack);

        String name = nbtItem.getString("FlanGuns_name");
        double range = nbtItem.getDouble("FlanGuns_range");
        double damage = nbtItem.getDouble("FlanGuns_damage");
        boolean place = nbtItem.getBoolean("FlanGuns_place");
        Particle particle = Particle.valueOf(nbtItem.getString("FlanGuns_particle"));
        ItemStack firstClone = itemStack.clone();
        firstClone.setAmount(1);

        Item item = player.getWorld().dropItem(player.getLocation().add(0, 1.5, 0), firstClone);

        if (place) {
            item.setVelocity(player.getLocation().getDirection().multiply(0.5));
        } else {
            item.setVelocity(player.getLocation().getDirection().multiply(1.02));
        }

        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1f, 1f);
        item.setPickupDelay(Integer.MAX_VALUE);

        if (!place) {
            item.setGlowing(true);
        }

        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
            ItemStack copy = itemStack.clone();
            copy.setAmount(itemStack.getAmount() - 1);
            player.getInventory().setItem(e.getSlot(), copy);
        }

        // Plaatsbare explosives zoals C4 en ClayMore worden opgeslagen in ExplosiveManager om hun locatie te onthouden
        if (place) {
            if (name.equals("C4_Plastic_Explosive")) {
                plugin.getExplosiveLocationManager().addC4(player, item);
            } else if (name.equals("M18_Claymore")) {
                plugin.getExplosiveLocationManager().addClaymore(item);
            }
        }

        // Geen plaatsbare explosive (Grenade / Molotov)
        else {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Location location = item.getLocation();
                item.remove();
                if (particle.equals(Particle.FLAME)) {
                    location.getWorld().spawnParticle(particle, location.clone().add(-1, 1, -1), 3);
                    location.getWorld().spawnParticle(particle, location.clone().add(0, 0.9, -0.7), 2);
                    location.getWorld().spawnParticle(particle, location.clone().add(0, 1.2, -1), 2);
                    location.getWorld().spawnParticle(particle, location.clone().add(-1, 0.7, 0), 3);
                    location.getWorld().spawnParticle(particle, location.clone().add(1, 0.8, 0), 3);
                    location.getWorld().spawnParticle(particle, location.clone().add(0, 0.9, 1), 3);
                    location.getWorld().spawnParticle(particle, location.clone().add(1, 1.1, 1), 2);
                } else {
                    location.getWorld().spawnParticle(particle, location, 1);
                }
                location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.3f, 1f);
                for (Entity entity : item.getNearbyEntities(range, range, range)) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if (livingEntity.getLocation().distance(item.getLocation()) > 0 && livingEntity.getLocation().distance(item.getLocation()) < 1) {
                            livingEntity.damage(1000000);
                        } else if (livingEntity.getLocation().distance(item.getLocation()) > 1 && livingEntity.getLocation().distance(item.getLocation()) < 1.5) {
                            livingEntity.damage(damage);
                        } else if (livingEntity.getLocation().distance(item.getLocation()) > 1.5 && livingEntity.getLocation().distance(item.getLocation()) < 2) {
                            livingEntity.damage(damage * 0.8);
                        } else if (livingEntity.getLocation().distance(item.getLocation()) > 2 && livingEntity.getLocation().distance(item.getLocation()) < 3) {
                            livingEntity.damage(damage * 0.6);
                        } else if (livingEntity.getLocation().distance(item.getLocation()) > 3 && livingEntity.getLocation().distance(item.getLocation()) <= 4) {
                            livingEntity.damage(damage * 0.4);
                        }
                        livingEntity.damage(damage);
                        livingEntity.setVelocity(livingEntity.getLocation().getDirection().rotateAroundX(180).rotateAroundY(180).rotateAroundZ(180).multiply(1.08).add(new Vector(0, 0.3, 0)));
                    }
                }

                // Molotov effect
                if (name.equals("Molotov")) {
                    location.getBlock().setType(Material.FIRE);
                    location.clone().add(0, 0, 1).getBlock().setType(Material.FIRE);
                    location.clone().add(1, 0, 0).getBlock().setType(Material.FIRE);
                    location.clone().add(1, 0, 1).getBlock().setType(Material.FIRE);
                    location.clone().add(-1, 0, 1).getBlock().setType(Material.FIRE);
                    location.clone().add(0, 0, -1).getBlock().setType(Material.FIRE);
                    location.clone().add(-1, 0, 0).getBlock().setType(Material.FIRE);
                }
            }, 30);
        }
    }
}