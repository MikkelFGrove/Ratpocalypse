package dk.lima.common.weapon;


import dk.lima.common.data.*;

public interface IWeaponSPI {
    void shoot(Entity e, GameData gameData, World world);
}
