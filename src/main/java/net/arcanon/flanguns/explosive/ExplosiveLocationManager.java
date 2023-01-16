package net.arcanon.flanguns.explosive;

import net.arcanon.flanguns.FlanGuns;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.*;

public class ExplosiveLocationManager {

    private final FlanGuns plugin;

    private Map<UUID, List<Item>> C4LocationList;
    private List<Item> claymoreLocationList;

    public ExplosiveLocationManager(FlanGuns plugin) {
        this.plugin = plugin;

        C4LocationList = new HashMap<>();
        claymoreLocationList = new ArrayList<>();
    }

    public List<Item> getC4s(Player player) {
        if (C4LocationList.containsKey(player.getUniqueId())) {
            return C4LocationList.get(player.getUniqueId());
        }
        return null;
    }

    public void addC4(Player player, Item item) {
        if (C4LocationList.containsKey(player.getUniqueId())) {
            List<Item> newList = C4LocationList.get(player.getUniqueId());
            newList.add(item);
            C4LocationList.replace(player.getUniqueId(), newList);
        } else {
            C4LocationList.put(player.getUniqueId(), new ArrayList() {{
                add(item);
            }});
        }
    }

    public void removeC4s(Player player) {
        if (!C4LocationList.containsKey(player.getUniqueId())) {
            return;
        }

        for (Item item : C4LocationList.get(player.getUniqueId())) {
            item.remove();
        }
        C4LocationList.remove(player.getUniqueId());
    }

    public boolean hasC4(Player player) {
        return C4LocationList.containsKey(player.getUniqueId());
    }

    public Item getClaymore(int x, int y, int z) {
        for (Item item : claymoreLocationList) {
            Location location = item.getLocation();
            if (location.getBlockX() == x && location.getBlockY() == y && location.getBlockZ() == z) {
                return item;
            }
        }
        return null;
    }

    public void addClaymore(Item item) {
        claymoreLocationList.add(item);
    }

    public void removeClaymore(int x, int y, int z) {
        for (Item item : claymoreLocationList) {
            if (item.getLocation().getBlockX() == x && item.getLocation().getBlockY() == y && item.getLocation().getBlockZ() == z) {
                item.remove();
                claymoreLocationList.remove(item);
            }
        }
    }

    public boolean hasClaymore(int x, int y, int z) {
        for (Item item : claymoreLocationList) {
            if (item.getLocation().getBlockX() == x && item.getLocation().getBlockY() == y && item.getLocation().getBlockZ() == z) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (List<Item> itemList : C4LocationList.values()) {
            for (Item item : itemList) {
                item.remove();
            }
        }

        for (Item item : claymoreLocationList) {
            item.remove();
        }

        C4LocationList.clear();
        claymoreLocationList.clear();
    }
}