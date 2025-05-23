import dk.lima.common.services.ITimeTask;
import dk.lima.wavespawner.WaveSpawnerTask;

module WaveSpawner {
    uses dk.lima.common.enemy.IEnemy;
    requires CommonEnemy;
    requires CommonEntityCP;
    requires Common;
    provides ITimeTask with WaveSpawnerTask;
}