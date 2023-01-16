package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.AUGAmmo;
import net.arcanon.flanguns.ammo.list.BarrettAmmo;
import net.arcanon.flanguns.ammo.list.BarrettExplosiveAmmo;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class Barrett extends Gun {

    public Barrett() {
        super("Barrett_.50cal", Material.DIAMOND_HOE, 304, new BarrettAmmo(), null, new BarrettExplosiveAmmo(), null, (short) 3, (short) 15, (short) 40, 25, "barrett.shoot", "barrett.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
