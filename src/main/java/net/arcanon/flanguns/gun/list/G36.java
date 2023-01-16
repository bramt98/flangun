package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.BarrettAmmo;
import net.arcanon.flanguns.ammo.list.BarrettExplosiveAmmo;
import net.arcanon.flanguns.ammo.list.G36Ammo;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class G36 extends Gun {

    public G36() {
        super("G36", Material.DIAMOND_HOE, 308, new G36Ammo(), null, null, null, (short) 1, (short) 2, (short) 40, 5, "g36.shoot", "g36.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
            add(new Foregrip());
        }});
    }
}
