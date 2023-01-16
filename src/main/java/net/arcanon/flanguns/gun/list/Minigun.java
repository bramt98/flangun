package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.M21Ammo;
import net.arcanon.flanguns.ammo.list.MinigunAmmo;
import net.arcanon.flanguns.ammo.list.MinigunExplosiveAmmo;
import net.arcanon.flanguns.attachment.list.ACOGSight;
import net.arcanon.flanguns.attachment.list.FourXSight;
import net.arcanon.flanguns.attachment.list.RedDotSight;
import net.arcanon.flanguns.attachment.list.Silencer;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class Minigun extends Gun {

    public Minigun() {
        super("Minigun", Material.DIAMOND_HOE, 312, new MinigunAmmo(), null, new MinigunExplosiveAmmo(), null, (short) 4, (short) 1, (short) 100, 2, "minigun.shoot", "minigun.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
        }});
    }
}
