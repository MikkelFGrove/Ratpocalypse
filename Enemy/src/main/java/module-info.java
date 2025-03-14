import dk.lima.common.enemy.IEnemy;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.enemy.EnemyPlugin;

module Enemy {
    requires Common;
    requires CommonEnemy;
    requires CommonWeapon;
    requires CommonPathfinding;
    provides IGamePluginService with dk.lima.enemy.EnemyPlugin;
    provides IEntityProcessingService with dk.lima.enemy.EnemyProcessor;
    provides IEnemy with EnemyPlugin;

    uses dk.lima.common.weapon.IWeaponSPI;
    uses dk.lima.common.pathfinding.IPathfindingSPI;
}