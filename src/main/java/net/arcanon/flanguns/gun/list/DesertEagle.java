package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.BarrettAmmo;
import net.arcanon.flanguns.ammo.list.BarrettExplosiveAmmo;
import net.arcanon.flanguns.ammo.list.DesertEagleAmmo;
import net.arcanon.flanguns.attachment.list.ACOGSight;
import net.arcanon.flanguns.attachment.list.FourXSight;
import net.arcanon.flanguns.attachment.list.RedDotSight;
import net.arcanon.flanguns.attachment.list.Silencer;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class DesertEagle extends Gun {

    public DesertEagle() {
        super("Desert_Eagle", Material.DIAMOND_HOE, 305, new DesertEagleAmmo(), null, null, null, (short) 6, (short) 3, (short) 40, 8, "deserteagle.shoot", "deserteagle.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
