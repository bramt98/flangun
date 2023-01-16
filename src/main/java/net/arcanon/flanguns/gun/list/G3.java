package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.BarrettAmmo;
import net.arcanon.flanguns.ammo.list.BarrettExplosiveAmmo;
import net.arcanon.flanguns.attachment.list.ACOGSight;
import net.arcanon.flanguns.attachment.list.FourXSight;
import net.arcanon.flanguns.attachment.list.RedDotSight;
import net.arcanon.flanguns.attachment.list.Silencer;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class G3 extends Gun {

    public G3() {
        super("G3", Material.DIAMOND_HOE, 307, new BarrettAmmo(), null, new BarrettExplosiveAmmo(), null, (short) 1, (short) 1, (short) 40, 7, "g3.shoot", "g3.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
