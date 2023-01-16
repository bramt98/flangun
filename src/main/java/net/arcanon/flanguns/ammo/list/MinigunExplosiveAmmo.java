package net.arcanon.flanguns.ammo.list;

import net.arcanon.flanguns.ammo.Ammo;
import org.bukkit.Material;

public class MinigunExplosiveAmmo extends Ammo {

    public MinigunExplosiveAmmo() {
        super("Explosive_Minigun_Ammo", Material.RABBIT_FOOT, 14, (short) 1, (short) 500, true, false, false);
    }
}
