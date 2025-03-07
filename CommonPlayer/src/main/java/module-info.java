import dk.lima.common.weapon.IWeaponSPI;

module CommonPlayer {
    requires Common;
    requires CommonWeapon;

    exports dk.lima.commonplayer;

    uses IWeaponSPI;
}