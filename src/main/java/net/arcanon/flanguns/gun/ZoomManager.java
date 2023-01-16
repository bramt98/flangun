package net.arcanon.flanguns.gun;

import net.arcanon.flanguns.FlanGuns;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// ZoomManager is om te checken of iemand in zoom zit of niet (met left-click)

public class ZoomManager {

    private final FlanGuns plugin;

    private Map<UUID, Boolean> inZoom;

    public ZoomManager(FlanGuns plugin) {
        this.plugin = plugin;
        inZoom = new HashMap<>();
    }

    public void addPlayer(Player player) {
        inZoom.put(player.getUniqueId(), false);
    }

    public boolean isInZoom(Player player) {
        return inZoom.get(player.getUniqueId());
    }

    public void setInZoom(Player player, boolean inZoom) {
        if (hasPlayer(player)) {
            this.inZoom.replace(player.getUniqueId(), inZoom);
        } else {
            this.inZoom.put(player.getUniqueId(), inZoom);
        }
    }

    public boolean hasPlayer(Player player) {
        return inZoom.containsKey(player.getUniqueId());
    }

    public void removePlayer(Player player) {
        inZoom.remove(player.getUniqueId());
    }
}