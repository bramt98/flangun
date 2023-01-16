package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.GlockAmmo;
import net.arcanon.flanguns.ammo.list.M21Ammo;
import net.arcanon.flanguns.attachment.list.ACOGSight;
import net.arcanon.flanguns.attachment.list.FourXSight;
import net.arcanon.flanguns.attachment.list.RedDotSight;
import net.arcanon.flanguns.attachment.list.Silencer;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class M21 extends Gun {

    public M21() {
        super("M21", Material.DIAMOND_HOE, 311, new M21Ammo(), null, null, null, (short) 2, (short) 15, (short) 70, 21, "m21.shoot", "m21.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
