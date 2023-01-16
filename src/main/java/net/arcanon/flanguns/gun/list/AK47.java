package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.AK47Ammo;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class AK47 extends Gun {

    public AK47() {
        super("AK47", Material.DIAMOND_HOE, 301, new AK47Ammo(), null, null, null, (short) 5, (short) 2, (short) 40, 7, "ak47.shoot", "ak47.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
