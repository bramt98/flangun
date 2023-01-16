package net.arcanon.flanguns.ammo;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.item.CustomItem;
import net.arcanon.flanguns.item.ItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public abstract class Ammo extends CustomItem {

    private short maxStackSize;
    private short roundsPerItem;
    private boolean explode;
    private boolean poison;
    private boolean fire;

    public Ammo(String name, Material material, int modelValue, short maxStackSize, short roundsPerItem, boolean explode, boolean poison, boolean fire) {
        super(name, ItemType.AMMO, material, modelValue);

        // MaxStackSize wordt wel onthouden voor elke ammo, maar doet niks omdat ik de buggy functionaliteit dus had weggehaald
        this.maxStackSize = maxStackSize;
        this.roundsPerItem = roundsPerItem;
        this.explode = explode;
        this.poison = poison;
        this.fire = fire;

        ItemStack itemStack = getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(new ArrayList() {{
            add("§7Rounds: " + roundsPerItem);
        }});
        itemStack.setItemMeta(itemMeta);

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setShort("FlanGuns_maxStackSize", maxStackSize);
        nbtItem.setShort("FlanGuns_roundsPerItem", roundsPerItem);
        nbtItem.setShort("FlanGuns_currentRounds", roundsPerItem);
        nbtItem.setBoolean("FlanGuns_explode", explode);
        nbtItem.setBoolean("FlanGuns_poison", poison);
        nbtItem.setBoolean("FlanGuns_fire", fire);

        setItemStack(nbtItem.getItem());
    }

    public short getMaxStackSize() {
        return maxStackSize;
    }

    public short getRoundsPerItem() {
        return roundsPerItem;
    }

    public boolean doesExplode() {
        return explode;
    }

    public boolean isPoison() {
        return poison;
    }

    public boolean isFire() {
        return fire;
    }
}