package net.arcanon.flanguns.ammo.list;

import net.arcanon.flanguns.ammo.Ammo;
import org.bukkit.Material;

public class GlockAmmo extends Ammo {

    public GlockAmmo() {
        super("Glock_Ammo", Material.RABBIT_FOOT, 10, (short) 5, (short) 31, false, false, false);
    }
}