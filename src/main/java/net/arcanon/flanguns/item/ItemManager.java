package net.arcanon.flanguns.item;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.FlanGuns;
import net.arcanon.flanguns.ammo.list.*;
import net.arcanon.flanguns.attachment.Attachment;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.explosive.list.*;
import net.arcanon.flanguns.gun.Gun;
import net.arcanon.flanguns.gun.list.*;
import net.arcanon.flanguns.medical.list.Medkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Van hieruit kun je makkelijk de Custom Items managen, checken en geven aan spelers

public class ItemManager {

    private final FlanGuns plugin;

    private List<CustomItem> itemList;
    private List<Attachment> attachmentList;

    public ItemManager(FlanGuns plugin) {
        this.plugin = plugin;
        itemList = new ArrayList<>();
        attachmentList = new ArrayList<>();

        // Ammo
        itemList.add(new AK47Ammo());
        itemList.add(new AK74Ammo());
        itemList.add(new AUGAmmo());
        itemList.add(new BarrettAmmo());
        itemList.add(new BarrettExplosiveAmmo());
        itemList.add(new DesertEagleAmmo());
        itemList.add(new FNSCARAmmo());
        itemList.add(new G3Ammo());
        itemList.add(new G36Ammo());
        itemList.add(new GlockAmmo());
        itemList.add(new M16A4Ammo());
        itemList.add(new M21Ammo());
        itemList.add(new MinigunAmmo());
        itemList.add(new MinigunExplosiveAmmo());
        itemList.add(new MP5Ammo());
        itemList.add(new MTARAmmo());
        itemList.add(new P90Ammo());
        itemList.add(new RPGAmmo());
        itemList.add(new SkorpionAmmo());
        itemList.add(new SPASAmmo());
        itemList.add(new USPAmmo());
        itemList.add(new USPPoisonAmmo());
        itemList.add(new UziAmmo());
        itemList.add(new W1200Ammo());
        itemList.add(new W1200IncendiaryAmmo());

        // Explosives
        itemList.add(new MkTwoFrag());
        itemList.add(new Molotov());
        itemList.add(new C4());
        itemList.add(new C4Remote());
        itemList.add(new Claymore());

        // Guns
        itemList.add(new AK47());
        itemList.add(new AK74());
        itemList.add(new AUG());
        itemList.add(new Barrett());
        itemList.add(new DesertEagle());
        itemList.add(new G3());
        itemList.add(new G36());
        itemList.add(new Glock());
        itemList.add(new M16A4());
        itemList.add(new M21());
        itemList.add(new Minigun());
        itemList.add(new MTAR());
        itemList.add(new P90());
        itemList.add(new RPG7());
        itemList.add(new Skorpion());
        itemList.add(new SPAS());
        itemList.add(new USP());
        itemList.add(new Uzi());
        itemList.add(new W1200());

        // Medicals
        itemList.add(new Medkit());

        // Attachments
        attachmentList.add(new ACOGSight());
        attachmentList.add(new Foregrip());
        attachmentList.add(new FourXSight());
        attachmentList.add(new RedDotSight());
        attachmentList.add(new Silencer());
    }

    public List<CustomItem> getItemList() {
        return itemList;
    }

    public boolean customItemExists(String name) {
        for (CustomItem customItem : itemList) {
            if (name.equalsIgnoreCase(customItem.getName())) {
                return true;
            }
        }
        return false;
    }

    public CustomItem getCustomItemByName(String name) {
        for (CustomItem customItem : itemList) {
            if (name.equalsIgnoreCase(customItem.getName())) {
                return customItem;
            }
        }
        return null;
    }

    public Gun getGun(ItemStack itemStack, UUID uuid) {
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName()) {
            return null;
        }

        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasNBTData()) {
            return null;
        }

        switch (itemStack.getItemMeta().getDisplayName()) {
            case "AK47":
                return new AK47();
            case "AK74":
                return new AK74();
            case "AUG":
                return new AUG();
            case "Barrett .50cal":
                return new Barrett();
            case "Desert Eagle":
                return new DesertEagle();
            case "FN SCAR":
                return new FNSCAR();
            case "G3":
                return new G3();
            case "G36":
                return new G36();
            case "Glock":
                return new Glock();
            case "M16A4":
                return new M16A4();
            case "M21":
                return new M21();
            case "Minigun":
                return new Minigun();
            case "MP5":
                return new MP5();
            case "MTAR":
                return new MTAR();
            case "P90":
                return new P90();
            case "RPG-7":
                return new RPG7();
            case "Skorpion":
                return new Skorpion();
            case "SPAS":
                return new SPAS();
            case "USP .45":
                return new USP();
            case "Mini-Uzi":
                return new Uzi();
            case "W1200":
                return new W1200();
        }

        return null;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }
}