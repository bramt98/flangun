package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.AK74Ammo;
import net.arcanon.flanguns.ammo.list.AUGAmmo;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class AUG extends Gun {

    public AUG() {
        super("AUG", Material.DIAMOND_HOE, 303, new AUGAmmo(), null, null, null, (short) 3, (short) 3, (short) 40, 8, "aug.shoot", "aug.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
            add(new Foregrip());
        }});
    }
}
