package net.arcanon.flanguns.listener;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.FlanGuns;
import net.arcanon.flanguns.event.FlanGunsC4RemoteEvent;
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
import org.bukkit.util.Vector;

public class C4RemoteListener implements Listener {

    private final FlanGuns plugin;

    public C4RemoteListener(FlanGuns plugin) {
        this.plugin = plugin;
    }

    // Runned als speler op C4Remote klikt
    @EventHandler
    public void onC4Remote(FlanGunsC4RemoteEvent e) {
        Player player = e.getPlayer();
        ExplosiveLocationManager explosiveLocationManager = plugin.getExplosiveLocationManager();

        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1.0f, 1.0f);
        if (explosiveLocationManager.hasC4(player)) {
            for (Item item : explosiveLocationManager.getC4s(player)) {
                Location location = item.getLocation();
                NBTItem nbtItem = new NBTItem(item.getItemStack());
                double range = nbtItem.getDouble("FlanGuns_range");
                double damage = nbtItem.getDouble("FlanGuns_damage");

                location.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, location, 1);
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
            }
            explosiveLocationManager.removeC4s(player);
        }
    }
}