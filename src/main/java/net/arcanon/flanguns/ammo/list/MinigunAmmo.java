package net.arcanon.flanguns.ammo.list;

import net.arcanon.flanguns.ammo.Ammo;
import org.bukkit.Material;

public class MinigunAmmo extends Ammo {

    public MinigunAmmo() {
        super("Minigun_Ammo", Material.RABBIT_FOOT, 13, (short) 1, (short) 500, false, false, false);
    }
}
