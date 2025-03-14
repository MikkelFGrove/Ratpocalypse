import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;

module Enemy {
    requires Common;
    requires CommonEnemy;
    requires CommonWeapon;
    requires CommonPathfinding;
    provides IGamePluginService with dk.lima.enemy.EnemyPlugin;
    provides IEntityProcessingService with dk.lima.enemy.EnemyProcessor;

    uses dk.lima.common.weapon.IWeaponSPI;
    uses dk.lima.common.pathfinding.IPathfindingSPI;
}