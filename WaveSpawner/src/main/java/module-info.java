import dk.lima.common.services.IWaveSpawner;
import dk.lima.wavespawner.WaveSpawner;

module WaveSpawner {
    uses dk.lima.common.enemy.IEnemy;
    requires Common;
    requires CommonEnemy;
    provides IWaveSpawner with WaveSpawner;
}