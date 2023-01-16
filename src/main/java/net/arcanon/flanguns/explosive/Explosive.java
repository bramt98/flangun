package net.arcanon.flanguns.explosive;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.item.CustomItem;
import net.arcanon.flanguns.item.ItemType;
import org.bukkit.Material;
import org.bukkit.Particle;

public class Explosive extends CustomItem {

    private double damage;
    private double range;
    private short maxStackSize;

    public Explosive(String name, Material material, int modelValue, double damage, double range, short maxStackSize, Particle particle, boolean place) {
        super(name, ItemType.EXPLOSIVE, material, modelValue);
        this.damage = damage;
        this.range = range;
        this.maxStackSize = maxStackSize;

        NBTItem nbtItem = getNbtItem();
        nbtItem.setDouble("FlanGuns_damage", damage);
        nbtItem.setDouble("FlanGuns_range", range);
        nbtItem.setString("FlanGuns_particle", particle.name());
        nbtItem.setBoolean("FlanGuns_place", place);
        nbtItem.setShort("FlanGuns_maxStackSize", maxStackSize);
        setItemStack(nbtItem.getItem());
    }

    public double getDamage() {
        return damage;
    }

    public double getRange() {
        return range;
    }

    public short getMaxStackSize() {
        return maxStackSize;
    }
}