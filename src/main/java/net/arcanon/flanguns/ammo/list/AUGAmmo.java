package net.arcanon.flanguns.ammo.list;

import net.arcanon.flanguns.ammo.Ammo;
import org.bukkit.Material;

public class AUGAmmo extends Ammo {

    public AUGAmmo() {
        super("AUG_Ammo", Material.RABBIT_FOOT, 3, (short) 3, (short) 42, false, false, false);
    }
}