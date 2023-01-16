package net.arcanon.flanguns.explosive.list;

import net.arcanon.flanguns.explosive.Explosive;
import org.bukkit.Material;
import org.bukkit.Particle;

public class C4 extends Explosive {

    public C4() {
        super("C4_Plastic_Explosive", Material.RABBIT_FOOT, 101, 8, 4, (short) 4, Particle.EXPLOSION_HUGE, true);
    }
}