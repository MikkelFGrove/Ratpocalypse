import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.weapon.IWeaponSPI;
import dk.lima.common.entitycomponents.*;

module CommonEntityCP {
    requires Common;
    requires CommonWeapon;
    requires CommonBullet;
    requires javafx.graphics;
    requires CommonPlayer;

    uses IWeaponSPI;

    exports dk.lima.common.entitycomponents;

    provides IEntityComponent with
            ShapeCP,
            TransformCP,
            WeaponCP,
            SpriteCP,
            HealthCP,
            DamageCP,
            BulletCP,
            MovementCP;
}


