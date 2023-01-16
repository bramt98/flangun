package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.MP5Ammo;
import net.arcanon.flanguns.ammo.list.MinigunAmmo;
import net.arcanon.flanguns.ammo.list.MinigunExplosiveAmmo;
import net.arcanon.flanguns.attachment.list.ACOGSight;
import net.arcanon.flanguns.attachment.list.FourXSight;
import net.arcanon.flanguns.attachment.list.RedDotSight;
import net.arcanon.flanguns.attachment.list.Silencer;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class MP5 extends Gun {

    public MP5() {
        super("MP5", Material.DIAMOND_HOE, 313, new MP5Ammo(), null, null, null, (short) 1, (short) 3, (short) 40, 6, "mp5.shoot", "mp5.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
