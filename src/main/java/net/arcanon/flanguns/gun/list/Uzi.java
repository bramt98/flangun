package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.SPASAmmo;
import net.arcanon.flanguns.ammo.list.UziAmmo;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class Uzi extends Gun {

    public Uzi() {
        super("Mini-Uzi", Material.DIAMOND_HOE, 320, new UziAmmo(), null, null, null, (short) 1, (short) 3, (short) 40, 5, "uzi.shoot", "uzi.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
