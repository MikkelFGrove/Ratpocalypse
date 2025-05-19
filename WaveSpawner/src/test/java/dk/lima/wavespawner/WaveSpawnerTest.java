package dk.lima.wavespawner;
import dk.lima.common.data.Coordinate;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.IEnemy;
import dk.lima.common.entity.Entity;
import dk.lima.common.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WaveSpawnerTest {
    private GameData gameData;
    private World world;
    private TestableWaveSpawnerTask waveSpawnerTask;

    @BeforeEach
    public void setup() {
        gameData = new GameData();
        world = new World();

        gameData.setCurrentWave(1);
        gameData.setGameRunning(true);
    }

    @Test
    public void testNewWave() {

        IEnemy melee = mock(IEnemy.class);
        IEnemy ranged = mock(IEnemy.class);
        IEnemy flock = mock(IEnemy.class);

        Entity dummyEntity = mock(Entity.class);
        when(melee.createEnemy(any(GameData.class), any(Coordinate.class))).thenReturn(dummyEntity);
        when(ranged.createEnemy(any(GameData.class), any(Coordinate.class))).thenReturn(dummyEntity);
        when(flock.createEnemy(any(GameData.class), any(Coordinate.class))).thenReturn(dummyEntity);
        when(dummyEntity.getID()).thenReturn("enemy-id");

        List<IEnemy> mockedEnemies = List.of(melee, ranged, flock);
        WaveSpawnerTask waveSpawnerTask = new TestableWaveSpawnerTask(mockedEnemies);

        waveSpawnerTask.setGameData(gameData);
        waveSpawnerTask.setWorld(world);

        waveSpawnerTask.run();

        assertFalse(world.getEntities().isEmpty());

        for (Entity e : world.getEntities()) {
            world.removeEntity(e);
        }

        waveSpawnerTask.run();

        assertFalse(world.getEntities().isEmpty());
    }

}
