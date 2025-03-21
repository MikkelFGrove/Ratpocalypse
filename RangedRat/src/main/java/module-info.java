import dk.lima.common.enemy.IEnemy;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.enemy.rangedrat.RangedRatPlugin;
import dk.lima.enemy.rangedrat.RangedRatProcessor;

module RangedRat {
    requires Common;
    requires CommonEnemy;
    requires CommonWeapon;
    requires CommonPathfinding;
    provides IGamePluginService with RangedRatPlugin;
    provides IEntityProcessingService with RangedRatProcessor;
    provides IEnemy with RangedRatPlugin;

    uses dk.lima.common.weapon.IWeaponSPI;
    uses dk.lima.common.pathfinding.IPathfindingSPI;
}