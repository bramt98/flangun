package net.arcanon.flanguns.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.FlanGuns;
import net.arcanon.flanguns.ReflectionsUtil;
import net.arcanon.flanguns.ammo.Ammo;
import net.arcanon.flanguns.attachment.list.Foregrip;
import net.arcanon.flanguns.event.FlanGunsShootEvent;
import net.arcanon.flanguns.event.FlanGunsZoomEvent;
import net.arcanon.flanguns.gun.AmmoManager;
import net.arcanon.flanguns.gun.Gun;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class GunListener implements Listener {

    private final FlanGuns plugin;

    public GunListener(FlanGuns plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onShoot(FlanGunsShootEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItemStack();
        NBTItem nbtItem = new NBTItem(itemStack);
        AmmoManager ammoManager = plugin.getAmmoManager();
        UUID uuid = UUID.fromString(nbtItem.getString("FlanGuns_gunUUID"));

        Gun gun = plugin.getItemManager().getGun(itemStack, uuid);
        if (gun == null) {
            return;
        }
        Ammo requiredAmmo = gun.getAmmoType();
        Ammo fireAmmo = gun.getFireType();
        Ammo explosiveAmmo = gun.getExplosiveType();
        Ammo poisonAmmo = gun.getPoisonType();

        // Register gun if needed
        if (!ammoManager.isRegistered(UUID.fromString(nbtItem.getString("FlanGuns_gunUUID")))) {
            ammoManager.register(UUID.fromString(nbtItem.getString("FlanGuns_gunUUID")), (short) 0);
        }

        // Needs to reload
        if (ammoManager.getAmmo(uuid) < 1) {

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
                        player.getInventory().setItem(e.getSlot(), nbtItem.getItem());

                        ammo = invContent;
                        break;
                    } else if (displayName.equals(newFireName)) {
                        hasAmmo = true;
                        nbtItem.setString("FlanGuns_bulletType", "FIRE");
                        player.getInventory().setItem(e.getSlot(), nbtItem.getItem());

                        ammo = invContent;
                        break;
                    } else if (displayName.equals(newExplosiveName)) {
                        hasAmmo = true;
                        nbtItem.setString("FlanGuns_bulletType", "EXPLOSIVE");
                        player.getInventory().setItem(e.getSlot(), nbtItem.getItem());

                        ammo = invContent;
                        break;
                    } else if (displayName.equals(newPoisonName)) {
                        hasAmmo = true;
                        nbtItem.setString("FlanGuns_bulletType", "POISON");
                        player.getInventory().setItem(e.getSlot(), nbtItem.getItem());

                        ammo = invContent;
                        break;
                    }
                }
            }

            // Heeft ammo in inventory
            if (hasAmmo) {

                // Is speler al aan het reloaden? -> cancel
                if (plugin.getReloadManager().isInReload(player)) {
                    return;
                }

                // Is speler in zoom? -> haal uit zoom
                if (plugin.getZoomManager().isInZoom(player)) {
                    plugin.getZoomManager().setInZoom(player, false);
                    player.removePotionEffect(PotionEffectType.SLOW);
                    player.getInventory().setItemInOffHand(null);
                }

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
//                    ammoClone.setDurability((short) (ammoClone.getType().getMaxDurability() / (newNbtAmmo.getShort("FlanGuns_currentRounds") / newNbtAmmo.getShort("FlanGuns_roundsPerItem"))));

                    ammo.setAmount(ammo.getAmount() - 1);
                    player.getInventory().addItem(ammoClone);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        ammoManager.setAmmo(uuid, gun.getAmmoType().getRoundsPerItem());
                        plugin.getReloadManager().setInReload(player, false);
                    }, gun.getReloadTime());
                }
            }

            // Heeft geen ammo in inventory
            else {
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1f, 0.9f);
                return;
            }
        }

        // Can shoot
        else {
            ammoManager.remove1Ammo(uuid);

            if (!nbtItem.getBoolean("FlanGuns_hasSilencer")) {
                player.getLocation().getWorld().playSound(player.getLocation(), gun.getShootSound(), 2f, 1f);
            }

            // Currently using regular ammo
            if (!nbtItem.getString("FlanGuns_bulletType").equals("EXPLOSIVE")) {
                new BukkitRunnable() {

                    Location loc = player.getLocation();

                    // Accuracy spread
                    double accuracyValue = ((double) gun.getAccuracy()) / 100;
                    Random random = new Random();
                    double valueX = (accuracyValue * -1) + (accuracyValue - (accuracyValue * -1)) * random.nextDouble();
                    double valueY = 0.5 * ((accuracyValue * -1) + (accuracyValue - (accuracyValue * -1)) * random.nextDouble());
                    double valueZ = (accuracyValue * -1) + (accuracyValue - (accuracyValue * -1)) * random.nextDouble();

                    Vector directionChange = loc.getDirection().add(new Vector(valueX, valueY, valueZ));

                    Vector dir = directionChange.normalize();
                    double t = 0;

                    // Bullet effect
                    @Override
                    public void run() {
                        t += 2;
                        double x = dir.getX() * t;
                        double y = dir.getY() * t + 1.5;
                        double z = dir.getZ() * t;
                        loc.add(x, y, z);

                        if (!loc.getBlock().isPassable() && !loc.getBlock().isLiquid()) {
                            this.cancel();
                        }

                        player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 0);

                        // Bullet hit detection
                        for (Entity ent : loc.getChunk().getEntities()) {
                            Vector vector = player.getLocation().getDirection().multiply(0.3).add(new Vector(0, 0.16, 0));
                            if (ent instanceof Player) {
                                Player player1 = (Player) ent;
                                if (player1.getLocation().distance(loc) < 1.4 && player1 != player) {
                                    player1.damage(gun.getDamage());

                                    if (!player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
                                        player1.setVelocity(vector);
                                    }

                                    if (nbtItem.getString("FlanGuns_bulletType").equals("FIRE")) {
                                        player1.setFireTicks(30);
                                    } else if (nbtItem.getString("FlanGuns_bulletType").equals("POISON")) {
                                        player1.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 1));
                                    }
                                }
                            } else if (ent instanceof LivingEntity) {
                                if (ent.getLocation().distance(loc) < 1.8) {
                                    Damageable ent2 = (Damageable) ent;
                                    ent2.damage(gun.getDamage());
                                    ent2.setVelocity(vector);

                                    if (nbtItem.getString("FlanGuns_bulletType").equals("FIRE")) {
                                        ent.setFireTicks(30);
                                    } else if (nbtItem.getString("FlanGuns_bulletType").equals("POISON")) {
                                        ((LivingEntity) ent).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 1));
                                    }
                                }
                            }
                        }

                        loc.subtract(x, y, z);
                        if (t > 80) {
                            this.cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0, 1);
            }

            // Recoil management
            float pitch;
            if (nbtItem.getBoolean("FlanGuns_hasForegrip")) {
                pitch = Math.round(((double) gun.getRecoil()) * new Foregrip().getRecoilMultiplier());
            } else {
                pitch = Math.round(gun.getRecoil());
            }
            addRecoilWithProtocolLib(player, pitch);
        }
    }

    // Add recoil in kleine stapjes met delayed tasks
    private void addRecoilWithProtocolLib(Player player, float pitch) {

        float pitchToUse = (pitch / 6) * -1;
        sendPitchChange(player, pitchToUse);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 1);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse * -2);
        }, 2);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            sendPitchChange(player, pitchToUse * -3);
        }, 2);
    }

    // Method om de pitch te changen
    private void sendPitchChange(Player player, float pitch) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        final PacketContainer yawPack = protocolManager.createPacket(PacketType.Play.Server.POSITION, false);

        yawPack.getDoubles().write(0, 0.0);
        yawPack.getDoubles().write(1, 0.0);
        yawPack.getDoubles().write(2, 0.0);
        yawPack.getFloat().write(0, 0f);
        yawPack.getFloat().write(1, pitch);

        Object[] enumObjects = ReflectionsUtil.getNmsClass("PacketPlayOutPosition$EnumPlayerTeleportFlags").getEnumConstants();
        Set<Object> teleportFlags = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(enumObjects[0], enumObjects[1], enumObjects[2], enumObjects[3], enumObjects[4])));
        yawPack.getModifier().write(5, teleportFlags);
        yawPack.getIntegers().write(0, 0);

        try {
            protocolManager.sendServerPacket(player, yawPack);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        plugin.getZoomManager().addPlayer(e.getPlayer());
        plugin.getReloadManager().addPlayer(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        plugin.getZoomManager().removePlayer(e.getPlayer());
        plugin.getReloadManager().removePlayer(e.getPlayer());
    }

    @EventHandler
    public void onZoom(FlanGunsZoomEvent e) {
        Player player = e.getPlayer();

        if (!plugin.getZoomManager().hasPlayer(player)) {
            return;
        }
        boolean inZoom = plugin.getZoomManager().isInZoom(player);

        AmmoManager ammoManager = plugin.getAmmoManager();
        ItemStack itemStack = e.getItemStack();
        NBTItem nbtItem = new NBTItem(itemStack);
        if (!ammoManager.isRegistered(UUID.fromString(nbtItem.getString("FlanGuns_gunUUID")))) {
            ammoManager.register(UUID.fromString(nbtItem.getString("FlanGuns_gunUUID")), (short) 0);
        }

        if (inZoom) {
            plugin.getZoomManager().setInZoom(player, false);
            player.removePotionEffect(PotionEffectType.SLOW);

            // Minigun slow effect
            if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().equals("Minigun")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
            }
            player.getInventory().setItemInOffHand(null);
        } else {
            plugin.getZoomManager().setInZoom(player, true);

            // Attachment Scope
            if (nbtItem.hasKey("FlanGuns_attachmentScope")) {
                ItemStack zoomItem = new ItemStack(Material.SLIME_BALL);
                ItemMeta itemMeta = zoomItem.getItemMeta();
                switch (nbtItem.getString("FlanGuns_attachmentScope")) {
                    case "ACOGSight":
                        itemMeta.setCustomModelData(1);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3));
                        break;
                    case "FourXSight":
                        itemMeta.setCustomModelData(2);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3));
                        break;
                    case "RedDotSight":
                        itemMeta.setCustomModelData(3);
                        break;
                }
                zoomItem.setItemMeta(itemMeta);
                player.getInventory().setItemInOffHand(zoomItem);
            }

            // Barrett Scope
            else if (itemStack.getItemMeta().getDisplayName().equals("Barrett .50cal")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3));
                ItemStack zoomItem = new ItemStack(Material.SLIME_BALL);
                ItemMeta itemMeta = zoomItem.getItemMeta();
                itemMeta.setCustomModelData(4);
                zoomItem.setItemMeta(itemMeta);
                player.getInventory().setItemInOffHand(zoomItem);
            }

            // M21 Scope
            else if (itemStack.getItemMeta().getDisplayName().equals("M21")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3));
                ItemStack zoomItem = new ItemStack(Material.SLIME_BALL);
                ItemMeta itemMeta = zoomItem.getItemMeta();
                itemMeta.setCustomModelData(5);
                zoomItem.setItemMeta(itemMeta);
                player.getInventory().setItemInOffHand(zoomItem);
            }

            else {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0));
            }
        }
    }
}