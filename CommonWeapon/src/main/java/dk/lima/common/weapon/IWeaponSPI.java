package dk.lima.common.weapon;

import dk.lima.common.data.*;
import dk.lima.common.entity.Entity;

public interface IWeaponSPI {
    void shoot(Entity e, GameData gameData, World world);
}
