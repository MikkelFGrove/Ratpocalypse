import dk.lima.common.weapon.IWeaponSPI;

module CommonEntityCP {
    requires Common;
    requires CommonWeapon;

    uses IWeaponSPI;

    exports dk.lima.common.entitycomponents;
}


