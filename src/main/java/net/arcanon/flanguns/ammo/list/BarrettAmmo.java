package net.arcanon.flanguns.ammo.list;

import net.arcanon.flanguns.ammo.Ammo;
import org.bukkit.Material;

public class BarrettAmmo extends Ammo {

    public BarrettAmmo() {
        super("Barrett_Ammo", Material.RABBIT_FOOT, 4, (short) 3, (short) 10, false, false, false);
    }
}
