package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.SPASAmmo;
import net.arcanon.flanguns.ammo.list.USPAmmo;
import net.arcanon.flanguns.ammo.list.USPPoisonAmmo;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class USP extends Gun {

    public USP() {
        super("USP_.45", Material.DIAMOND_HOE, 319, new USPAmmo(), null, null, new USPPoisonAmmo(), (short) 8, (short) 1, (short) 30, 6, "usp.shoot", "usp.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
