package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.SPASAmmo;
import net.arcanon.flanguns.ammo.list.SkorpionAmmo;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class SPAS extends Gun {

    public SPAS() {
        super("SPAS", Material.DIAMOND_HOE, 318, new SPASAmmo(), null, null, null, (short) 25, (short) 13, (short) 126, 7, "spas.shoot", "spas.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
            add(new Foregrip());
        }});
    }
}
