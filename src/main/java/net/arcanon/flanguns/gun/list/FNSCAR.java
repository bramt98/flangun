package net.arcanon.flanguns.gun.list;

import net.arcanon.flanguns.ammo.list.BarrettAmmo;
import net.arcanon.flanguns.ammo.list.BarrettExplosiveAmmo;
import net.arcanon.flanguns.ammo.list.FNSCARAmmo;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.gun.Gun;
import org.bukkit.Material;

import java.util.ArrayList;

public class FNSCAR extends Gun {

    public FNSCAR() {
        super("FN_SCAR", Material.DIAMOND_HOE, 306, new FNSCARAmmo(), null, null, null, (short) 3, (short) 3, (short) 40, 8, "fnscar.shoot", "fnscar.reload", new ArrayList() {{
            add(new RedDotSight());
            add(new FourXSight());
            add(new ACOGSight());
            add(new Silencer());
            add(new Foregrip());
        }});
    }
}
