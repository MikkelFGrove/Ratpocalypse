import dk.lima.common.bullet.IBulletSPI;
import dk.lima.weapon.Rifle;

module Weapon {
    requires CommonWeapon;
    requires Common;
    requires CommonBullet;
    provides dk.lima.common.weapon.IWeaponSPI with Rifle;

    uses IBulletSPI;
}