package net.arcanon.flanguns.gun;

import net.arcanon.flanguns.FlanGuns;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// AmmoManager onthoudt ammo voor elke gun o.b.v. de UUID van een gun

public class AmmoManager {

    private final FlanGuns plugin;

    private Map<UUID, Short> ammoMap;

    public AmmoManager(FlanGuns plugin) {
        this.plugin = plugin;
        ammoMap = new HashMap<>();
    }

    public void register(UUID uuid, short ammo) {
        ammoMap.put(uuid, ammo);
    }

    public void setAmmo(UUID uuid, short ammo) {
        ammoMap.replace(uuid, ammo);
    }

    public void remove1Ammo(UUID uuid) {
        ammoMap.replace(uuid, (short) (ammoMap.get(uuid) - 1));
    }

    public short getAmmo(UUID uuid) {
        return ammoMap.get(uuid);
    }

    public boolean isRegistered(UUID uuid) {
        return ammoMap.containsKey(uuid);
    }

    public void unRegister(UUID uuid) {
        ammoMap.remove(uuid);
    }
}