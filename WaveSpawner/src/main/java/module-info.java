import dk.lima.common.services.ITimeTask;
import dk.lima.wavespawner.WaveSpawnerTask;

module WaveSpawner {
    requires CommonEnemy;
    requires CommonEntityCP;
    requires Common;

    provides ITimeTask with WaveSpawnerTask;
    uses dk.lima.common.enemy.IEnemy;
}