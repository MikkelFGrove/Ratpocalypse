import dk.lima.common.enemy.IEnemy;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.flockRat.FlockRatPlugin;
import dk.lima.flockRat.FlockRatProcessor;

module FlockRat {
    requires Common;
    requires CommonEntityCP;
    requires CommonPathfinding;
    requires CommonEnemy;
    requires CommonWeapon;
    requires FlockComponent;

    provides IGamePluginService with FlockRatPlugin;
    provides IEntityProcessingService with FlockRatProcessor;
    provides IEnemy with FlockRatPlugin;

    uses dk.lima.common.weapon.IWeaponSPI;
    uses dk.lima.common.entity.IEntityComponent;
}