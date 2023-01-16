package net.arcanon.flanguns.ammo.list;

import net.arcanon.flanguns.ammo.Ammo;
import org.bukkit.Material;

public class AK47Ammo extends Ammo {

    public AK47Ammo() {
        super("AK47_Ammo", Material.RABBIT_FOOT, 1, (short) 4, (short) 30, false, false, false);
    }
}
