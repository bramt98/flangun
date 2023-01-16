package net.arcanon.flanguns.medical;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.item.CustomItem;
import net.arcanon.flanguns.item.ItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Medical extends CustomItem {

    private double healAmount;

    public Medical(String name, Material material, int modelValue, double healAmount) {
        super(name, ItemType.MEDICAL, material, modelValue);
        this.healAmount = healAmount;

        NBTItem nbtItem = getNbtItem();
        nbtItem.setDouble("FlanGuns_healAmount", healAmount);
        nbtItem.setInteger("FlanGuns_uses", 3);

        ItemStack itemStack = nbtItem.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(new ArrayList() {{
            add("§7Heals §c" + healAmount/2 + " ❤");
        }});
        itemStack.setItemMeta(itemMeta);
        setItemStack(itemStack);
    }

    public double getHealAmount() {
        return healAmount;
    }
}