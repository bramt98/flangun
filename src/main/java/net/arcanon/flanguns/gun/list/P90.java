package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.MTARAmmo;
import net.arcanon.flanguns.ammo.list.P90Ammo;
import net.arcanon.flanguns.attachment.list.ACOGSight;
import net.arcanon.flanguns.attachment.list.FourXSight;
import net.arcanon.flanguns.attachment.list.RedDotSight;
import net.arcanon.flanguns.attachment.list.Silencer;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class P90 extends Gun {

    public P90() {
        super("P90", Material.DIAMOND_HOE, 315, new P90Ammo(), null, null, null, (short) 1, (short) 2, (short) 50, 5, "p90.shoot", "p90.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
