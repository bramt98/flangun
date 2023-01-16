package net.arcanon.flanguns.ammo.list;

import net.arcanon.flanguns.ammo.Ammo;
import org.bukkit.Material;

public class BarrettExplosiveAmmo extends Ammo {

    public BarrettExplosiveAmmo() {
        super("Explosive_Barrett_Ammo", Material.RABBIT_FOOT, 5, (short) 3, (short) 10, true, false, false);
    }
}
