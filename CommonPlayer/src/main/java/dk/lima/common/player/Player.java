package dk.lima.common.player;

import dk.lima.common.data.Entity;
import dk.lima.common.weapon.IWeaponSPI;

public class Player extends Entity {
    private IWeaponSPI iWeaponSPI;

    public void setIWeaponSPI(IWeaponSPI iWeaponSPI) {
        this.iWeaponSPI = iWeaponSPI;
    }
    public IWeaponSPI getIWeaponSPI() {
        return iWeaponSPI;
    }
}
