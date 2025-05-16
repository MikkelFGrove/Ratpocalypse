import dk.lima.common.enemy.IEnemy;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.flockRat.*;

module FlockRat {
    requires Common;
    requires CommonEntityCP;
    requires CommonPathfinding;
    requires CommonEnemy;
    requires CommonWeapon;

    provides IGamePluginService with FlockRatPlugin;
    provides IEntityProcessingService with FlockRatProcessor;
    provides IEnemy with FlockRatPlugin;
    provides IEntityComponent with FlockCP;

    uses dk.lima.common.weapon.IWeaponSPI;
    uses dk.lima.common.entity.IEntityComponent;
}