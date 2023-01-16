package net.arcanon.flanguns.gun;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.ammo.Ammo;
import net.arcanon.flanguns.attachment.Attachment;
import net.arcanon.flanguns.item.CustomItem;
import net.arcanon.flanguns.item.ItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class Gun extends CustomItem {

    private Ammo ammoType;
    private Ammo fireType;
    private Ammo explosiveType;
    private Ammo poisonType;

    private short accuracy;
    private short recoil;
    private short reloadTime;
    private double damage;

    private String shootSound;
    private String reloadSound;

    private List<Attachment> allowedAttachments;

    public Gun(String name, Material material, int modelValue, Ammo ammoType, Ammo fireType, Ammo explosiveType, Ammo poisonType, short accuracy, short recoil, short reloadTime, double damage, String shootSound, String reloadSound, List<Attachment> allowedAttachments) {
        super(name, ItemType.GUN, material, modelValue);

        this.ammoType = ammoType;
        this.fireType = fireType;
        this.explosiveType = explosiveType;
        this.poisonType = poisonType;

        this.accuracy = accuracy;
        this.recoil = recoil;
        this.reloadTime = reloadTime;
        this.damage = damage;
        this.shootSound = shootSound;
        this.reloadSound = reloadSound;
        this.allowedAttachments = allowedAttachments;

        NBTItem nbtItem = new NBTItem(getItemStack());
        nbtItem.setShort("FlanGuns_maxAmmo", ammoType.getRoundsPerItem());
        nbtItem.setString("FlanGuns_ammoType", ammoType.getName());
        nbtItem.setShort("FlanGuns_accuracy", accuracy);
        nbtItem.setShort("FlanGuns_recoil", recoil);
        nbtItem.setShort("FlanGuns_reloadTime", reloadTime);
        nbtItem.setDouble("FlanGuns_damage", damage);
        nbtItem.setString("FlanGuns_shootSound", shootSound);
        nbtItem.setString("FlanGuns_reloadSound", reloadSound);
        nbtItem.setBoolean("FlanGuns_hasForegrip", false);
        nbtItem.setBoolean("FlanGuns_hasSilencer", false);
        nbtItem.setString("FlanGuns_bulletType", "REGULAR");

        StringBuilder allowedAttachmentString = new StringBuilder();
        int i = 0;
        for (Attachment attachment : allowedAttachments) {
            if (i > 0) {
                allowedAttachmentString.append(";");
            }
            allowedAttachmentString.append(attachment.getName());
            i++;
        }
        nbtItem.setString("FlanGuns_allowedAttachments", allowedAttachmentString.toString());

        setItemStack(nbtItem.getItem());

        ItemStack current = getItemStack();
        ItemMeta itemMeta = current.getItemMeta();
        itemMeta.setLore(new ArrayList() {{
            add("§9Scope: §7-");
            add("§9Attachments: §7-");
        }});
        current.setItemMeta(itemMeta);
        setItemStack(current);
    }

    public Ammo getAmmoType() {
        return ammoType;
    }

    public void setAmmoType(Ammo ammoType) {
        this.ammoType = ammoType;
    }

    public short getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(short accuracy) {
        this.accuracy = accuracy;
    }

    public short getRecoil() {
        return recoil;
    }

    public void setRecoil(short recoil) {
        this.recoil = recoil;
    }

    public short getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(short reloadTime) {
        this.reloadTime = reloadTime;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public String getShootSound() {
        return shootSound;
    }

    public void setShootSound(String shootSound) {
        this.shootSound = shootSound;
    }

    public String getReloadSound() {
        return reloadSound;
    }

    public void setReloadSound(String reloadSound) {
        this.reloadSound = reloadSound;
    }

    public List<Attachment> getAllowedAttachments() {
        return allowedAttachments;
    }

    public void setAllowedAttachments(List<Attachment> allowedAttachments) {
        this.allowedAttachments = allowedAttachments;
    }

    public Ammo getFireType() {
        return fireType;
    }

    public Ammo getExplosiveType() {
        return explosiveType;
    }

    public Ammo getPoisonType() {
        return poisonType;
    }
}