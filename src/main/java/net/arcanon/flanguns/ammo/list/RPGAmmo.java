package net.arcanon.flanguns.ammo.list;

import net.arcanon.flanguns.ammo.Ammo;
import org.bukkit.Material;

public class RPGAmmo extends Ammo {

    public RPGAmmo() {
        super("RPG_Ammo", Material.RABBIT_FOOT, 18, (short) 2, (short) 1, true, false, false);
    }
}
