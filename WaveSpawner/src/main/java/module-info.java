import dk.lima.common.services.ITimeTask;
import dk.lima.wavespawner.WaveSpawnerTask;

module WaveSpawner {
    uses dk.lima.common.enemy.IEnemy;
    requires Common;
    requires CommonEnemy;
    provides ITimeTask with WaveSpawnerTask;
}