package net.arcanon.flanguns.explosive.list;

import net.arcanon.flanguns.explosive.Explosive;
import org.bukkit.Material;
import org.bukkit.Particle;

public class Molotov extends Explosive {

    public Molotov() {
        super("Molotov", Material.RABBIT_FOOT, 105, 4, 2, (short) 4, Particle.FLAME, false);
    }
}