package net.arcanon.flanguns.explosive.list;

import net.arcanon.flanguns.explosive.Explosive;
import org.bukkit.Material;
import org.bukkit.Particle;

public class Claymore extends Explosive {

    public Claymore() {
        super("M18_Claymore", Material.RABBIT_FOOT, 103, 5, 3, (short) 3, Particle.EXPLOSION_HUGE, true);
    }
}