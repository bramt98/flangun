package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.UziAmmo;
import net.arcanon.flanguns.ammo.list.W1200Ammo;
import net.arcanon.flanguns.ammo.list.W1200IncendiaryAmmo;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class W1200 extends Gun {

    public W1200() {
        super("W1200", Material.DIAMOND_HOE, 321, new W1200Ammo(), new W1200IncendiaryAmmo(), null, null, (short) 11, (short) 10, (short) 126, 6, "w1200.shoot", "w1200.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
            add(new Foregrip());
        }});
    }
}
