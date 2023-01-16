package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.AK47Ammo;
import net.arcanon.flanguns.ammo.list.AK74Ammo;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class AK74 extends Gun {

    public AK74() {
        super("AK74", Material.DIAMOND_HOE, 302, new AK74Ammo(), null, null, null, (short) 2, (short) 3, (short) 40, 6, "ak74.shoot", "ak74.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
