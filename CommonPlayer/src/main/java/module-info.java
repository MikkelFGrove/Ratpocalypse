import dk.lima.common.weapon.IWeaponSPI;

module CommonPlayer {
    requires Common;
    requires CommonWeapon;

    exports dk.lima.common.player;

    uses IWeaponSPI;
}