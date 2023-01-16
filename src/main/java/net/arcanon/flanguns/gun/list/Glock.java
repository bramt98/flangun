package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.G36Ammo;
import net.arcanon.flanguns.ammo.list.GlockAmmo;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class Glock extends Gun {

    public Glock() {
        super("Glock", Material.DIAMOND_HOE, 309, new GlockAmmo(), null, null, null, (short) 1, (short) 2, (short) 32, 5, "glock.shoot", "glock.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
