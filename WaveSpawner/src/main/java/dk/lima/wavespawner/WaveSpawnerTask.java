package dk.lima.wavespawner;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.IEnemy;
import dk.lima.common.services.ITimeTask;


import java.util.Collection;
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
        getEnemies().stream().findFirst().ifPresent(iEnemy -> {world.addEntity(iEnemy.createEnemy(gameData, world));});
    }

    private Collection<? extends IEnemy> getEnemies() {
        return ServiceLoader.load(IEnemy.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }
}
