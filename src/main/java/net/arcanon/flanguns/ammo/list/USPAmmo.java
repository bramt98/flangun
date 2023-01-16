package net.arcanon.flanguns.ammo.list;

import net.arcanon.flanguns.ammo.Ammo;
import org.bukkit.Material;

public class USPAmmo extends Ammo {

    public USPAmmo() {
        super("USP_Ammo", Material.RABBIT_FOOT, 21, (short) 5, (short) 10, false, false, false);
    }
}
