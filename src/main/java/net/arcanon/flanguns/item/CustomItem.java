package net.arcanon.flanguns.item;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

// Deze class vormt de basis voor wat elk CustomItem heeft

public abstract class CustomItem {

    private String name;
    private ItemType type;
    private int modelValue;

    private ItemStack itemStack;

    public CustomItem(String name, ItemType type, Material material, int modelValue) {
        this.name = name;
        this.type = type;
        this.modelValue = modelValue;

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String newName = name.replace("_", " ");
        itemMeta.setDisplayName("§f" + newName);
        itemMeta.setCustomModelData(modelValue);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.values());
        itemStack.setItemMeta(itemMeta);

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("FlanGuns_name", name);
        nbtItem.setString("FlanGuns_type", type.name());
        itemStack = nbtItem.getItem();

        this.itemStack = itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public int getModelValue() {
        return modelValue;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public NBTItem getNbtItem() {
        return new NBTItem(itemStack);
    }
}