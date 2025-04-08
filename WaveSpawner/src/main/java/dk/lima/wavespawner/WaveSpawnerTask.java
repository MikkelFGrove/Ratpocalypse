package dk.lima.wavespawner;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.IEnemy;
import dk.lima.common.services.ITimeTask;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class WaveSpawnerTask implements ITimeTask {
    private World world;
    private GameData gameData;

    @Override
    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public long getDelay() {
        return 0;
    }

    @Override
    public long getPeriod() {
        return 5;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return TimeUnit.SECONDS;
    }

    @Override
    public void run() {
        ArrayList<IEnemy> enemies = new ArrayList<>(getEnemies());

        if (gameData.isGameRunning() & !enemies.isEmpty()) {
            Random rand = new Random();

            if (gameData.getDuration().toSeconds() % 30 >= 25) {
                for (int i = 0; i < 10; i++) {
                    int randNum = rand.nextInt(0, getEnemies().size());
                    world.addEntity(enemies.get(randNum).createEnemy(gameData, world));
                }
            } else {
                int randNum = rand.nextInt(0, getEnemies().size());
                world.addEntity(enemies.get(randNum).createEnemy(gameData, world));
            }
        }
    }

    private Collection<? extends IEnemy> getEnemies() {
        return ServiceLoader.load(IEnemy.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }
}
