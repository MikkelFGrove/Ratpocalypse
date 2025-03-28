import dk.lima.common.enemy.IEnemy;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.meleerat.MeleeRatPlugin;
import dk.lima.meleerat.MeleeRatProcessor;

module MeleeRat {
    requires Common;
    requires CommonEntityCP;
    requires CommonEnemy;
    requires CommonWeapon;

    provides IGamePluginService with MeleeRatPlugin;
    provides IEntityProcessingService with MeleeRatProcessor;
    provides IEnemy with MeleeRatPlugin;

    uses dk.lima.common.weapon.IWeaponSPI;
    uses dk.lima.common.data.IEntityComponent;
}