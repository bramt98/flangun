package net.arcanon.flanguns.explosive.list;

import net.arcanon.flanguns.explosive.Explosive;
import org.bukkit.Material;
import org.bukkit.Particle;

public class MkTwoFrag extends Explosive {

    public MkTwoFrag() {
        super("Mk.2_Frag_Grenade", Material.RABBIT_FOOT, 104, 8, 4, (short) 4, Particle.EXPLOSION_HUGE, false);
    }
}