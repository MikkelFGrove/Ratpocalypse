import dk.lima.common.weapon.IWeaponSPI;

module CommonEntityCP {
    requires Common;
    requires CommonWeapon;
    requires javafx.graphics;

    uses IWeaponSPI;

    exports dk.lima.common.entitycomponents;
}


