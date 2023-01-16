package net.arcanon.flanguns.ammo.list;

import net.arcanon.flanguns.ammo.Ammo;
import org.bukkit.Material;

public class USPPoisonAmmo extends Ammo {

    public USPPoisonAmmo() {
        super("USP_Poison_Ammo", Material.RABBIT_FOOT, 22, (short) 5, (short) 10, false, true, false);
    }
}
