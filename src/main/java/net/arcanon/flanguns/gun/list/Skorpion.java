package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.P90Ammo;
import net.arcanon.flanguns.ammo.list.SkorpionAmmo;
import net.arcanon.flanguns.attachment.list.ACOGSight;
import net.arcanon.flanguns.attachment.list.FourXSight;
import net.arcanon.flanguns.attachment.list.RedDotSight;
import net.arcanon.flanguns.attachment.list.Silencer;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class Skorpion extends Gun {

    public Skorpion() {
        super("Skorpion", Material.DIAMOND_HOE, 317, new SkorpionAmmo(), null, null, null, (short) 1, (short) 2, (short) 40, 7, "skorpion.shoot", "skorpion.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
