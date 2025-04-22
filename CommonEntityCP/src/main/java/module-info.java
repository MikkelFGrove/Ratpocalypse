import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.weapon.IWeaponSPI;
import dk.lima.common.entitycomponents.*;

module CommonEntityCP {
    requires Common;
    requires CommonWeapon;
    requires javafx.graphics;

    uses IWeaponSPI;

    exports dk.lima.common.entitycomponents;

    provides IEntityComponent with ShapeCP, TransformCP, WeaponCP, SpriteCP;
}


