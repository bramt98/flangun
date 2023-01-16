package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.GlockAmmo;
import net.arcanon.flanguns.ammo.list.M16A4Ammo;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class M16A4 extends Gun {

    public M16A4() {
        super("M16A4", Material.DIAMOND_HOE, 310, new M16A4Ammo(), null, null, null, (short) 2, (short) 2, (short) 40, 7, "m16a4.shoot", "m16a4.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
            add(new Foregrip());
        }});
    }
}
