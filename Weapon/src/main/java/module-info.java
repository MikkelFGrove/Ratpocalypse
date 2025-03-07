import dk.lima.weapon.Rifle;

module Weapon {
    requires CommonWeapon;
    requires Common;
    requires CommonBullet;
    provides dk.lima.common.weapon.IWeaponSPI with Rifle;

    uses dk.lima.commonBullet.IBulletSPI;
}