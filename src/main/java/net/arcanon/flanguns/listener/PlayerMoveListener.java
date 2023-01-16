package net.arcanon.flanguns.listener;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.FlanGuns;
import net.arcanon.flanguns.explosive.ExplosiveLocationManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerMoveListener implements Listener {

    private final FlanGuns plugin;

    public PlayerMoveListener(FlanGuns plugin) {
        this.plugin = plugin;
    }

    // Check of speler op een Claymore staat
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom() != e.getTo()) {
            ExplosiveLocationManager explosiveLocationManager = plugin.getExplosiveLocationManager();

            if (explosiveLocationManager.hasClaymore(e.getTo().getBlockX(), e.getTo().getBlockY(), e.getTo().getBlockZ())) {
                Item item = explosiveLocationManager.getClaymore(e.getTo().getBlockX(), e.getTo().getBlockY(), e.getTo().getBlockZ());
                Location location = e.getTo();
                NBTItem nbtItem = new NBTItem(item.getItemStack());
                double range = nbtItem.getDouble("FlanGuns_range");
                double damage = nbtItem.getDouble("FlanGuns_damage");

                location.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, location, 1);
                location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.3f, 1f);
                for (Entity entity : item.getNearbyEntities(range, range, range)) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if (livingEntity.getLocation().distance(item.getLocation()) > 0 && livingEntity.getLocation().distance(item.getLocation()) < 1) {
                            livingEntity.damage(damage);
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
                explosiveLocationManager.removeClaymore(e.getTo().getBlockX(), e.getTo().getBlockY(), e.getTo().getBlockZ());
            }
        }
    }
}