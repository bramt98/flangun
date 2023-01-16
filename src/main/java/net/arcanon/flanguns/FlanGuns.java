package net.arcanon.flanguns;

import net.arcanon.flanguns.command.FlanGunsCommand;
import net.arcanon.flanguns.explosive.ExplosiveLocationManager;
import net.arcanon.flanguns.gun.AmmoManager;
import net.arcanon.flanguns.gun.ReloadManager;
import net.arcanon.flanguns.gun.ZoomManager;
import net.arcanon.flanguns.item.ItemManager;
import net.arcanon.flanguns.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class FlanGuns extends JavaPlugin {

    private ItemManager itemManager;
    private ZoomManager zoomManager;
    private ReloadManager reloadManager;
    private ExplosiveLocationManager explosiveLocationManager;
    private AmmoManager ammoManager;

    public ItemManager getItemManager() {
        return itemManager;
    }

    public ReloadManager getReloadManager() {
        return reloadManager;
    }

    public ZoomManager getZoomManager() {
        return zoomManager;
    }

    public ExplosiveLocationManager getExplosiveLocationManager() {
        return explosiveLocationManager;
    }

    public AmmoManager getAmmoManager() {
        return ammoManager;
    }

    @Override
    public void onEnable() {
        registerEvents();
        registerCommands();

        this.itemManager = new ItemManager(this);
        this.zoomManager = new ZoomManager(this);
        this.reloadManager = new ReloadManager(this);
        this.explosiveLocationManager = new ExplosiveLocationManager(this);
        this.ammoManager = new AmmoManager(this);

        getServer().getConsoleSender().sendMessage("[FlanGuns] §aPlugin successfully enabled!");

        for (Player player : Bukkit.getOnlinePlayers()) {
            zoomManager.addPlayer(player);
            reloadManager.addPlayer(player);
        }
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new ItemClickListener(), this);
        getServer().getPluginManager().registerEvents(new MedicalUseListener(), this);
        getServer().getPluginManager().registerEvents(new GrenadeThrowListener(this), this);
        getServer().getPluginManager().registerEvents(new C4RemoteListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new SlotSwitchListener(this), this);
        getServer().getPluginManager().registerEvents(new GunListener(this), this);
        getServer().getPluginManager().registerEvents(new SwitchHandsListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDropListener(this), this);
    }

    private void registerCommands() {
        getCommand("flanguns").setExecutor(new FlanGunsCommand(this));
    }

    @Override
    public void onDisable() {
        explosiveLocationManager.clear();
        getServer().getConsoleSender().sendMessage("[FlanGuns] §cPlugin is now disabled!");

        for (Player player : Bukkit.getOnlinePlayers()) {
            zoomManager.removePlayer(player);
            reloadManager.removePlayer(player);
        }
    }
}