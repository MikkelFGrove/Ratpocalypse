package dk.lima.wavespawner;

import dk.lima.common.enemy.IEnemy;

import java.util.Collection;
import java.util.List;

class TestableWaveSpawnerTask extends WaveSpawnerTask {
    private final List<IEnemy> testEnemies;

    public TestableWaveSpawnerTask(List<IEnemy> testEnemies) {
        this.testEnemies = testEnemies;
    }

    @Override
    protected Collection<? extends IEnemy> getEnemies() {
        return testEnemies;
    }
}