package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.MP5Ammo;
import net.arcanon.flanguns.ammo.list.MTARAmmo;
import net.arcanon.flanguns.attachment.list.ACOGSight;
import net.arcanon.flanguns.attachment.list.FourXSight;
import net.arcanon.flanguns.attachment.list.RedDotSight;
import net.arcanon.flanguns.attachment.list.Silencer;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class MTAR extends Gun {

    public MTAR() {
        super("MTAR", Material.DIAMOND_HOE, 314, new MTARAmmo(), null, null, null, (short) 3, (short) 3, (short) 40, 8, "mtar.shoot", "mtar.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
