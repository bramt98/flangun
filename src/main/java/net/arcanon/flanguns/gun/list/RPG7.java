package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.RPGAmmo;
import net.arcanon.flanguns.attachment.list.ACOGSight;
import net.arcanon.flanguns.attachment.list.FourXSight;
import net.arcanon.flanguns.attachment.list.RedDotSight;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class RPG7 extends Gun {

    public RPG7() {
        super("RPG-7", Material.DIAMOND_HOE, 316, new RPGAmmo(), null, null, null, (short) 20, (short) 25, (short) 80, 1, "rpg.shoot", "rpg.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
        }});
    }
}
