package net.arcanon.flanguns.gun;

import net.arcanon.flanguns.FlanGuns;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// ReloadManager is voor het checken of iemand aan het reloaden is

public class ReloadManager {

    private final FlanGuns plugin;

    private Map<UUID, Boolean> inReload;

    public ReloadManager(FlanGuns plugin) {
        this.plugin = plugin;
        inReload = new HashMap<>();
    }

    public void addPlayer(Player player) {
        inReload.put(player.getUniqueId(), false);
    }

    public boolean isInReload(Player player) {
        return inReload.get(player.getUniqueId());
    }

    public void setInReload(Player player, boolean inReload) {
        if (hasPlayer(player)) {
            this.inReload.replace(player.getUniqueId(), inReload);
        } else {
            this.inReload.put(player.getUniqueId(), inReload);
        }
    }

    public boolean hasPlayer(Player player) {
        return inReload.containsKey(player.getUniqueId());
    }

    public void removePlayer(Player player) {
        inReload.remove(player.getUniqueId());
    }
}