package dk.lima.wavespawner;
import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.IEnemy;
import dk.lima.common.entity.Entity;
import dk.lima.common.services.ITimeTask;


import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class WaveSpawnerTask implements ITimeTask {
    private World world;
    private GameData gameData;

    private List<List<IEnemy>> manualWaves = new ArrayList<>();
    private boolean wavesInitialized = false;

    private void manualWaves() {
        List<IEnemy> allEnemies = new ArrayList<>(getEnemies());
        IEnemy RangedRat = allEnemies.get(0);
        IEnemy MeleeRat = allEnemies.get(1);
        IEnemy FlockRat = allEnemies.get(2);

        manualWaves.add(List.of(MeleeRat));
        manualWaves.add(List.of(MeleeRat, RangedRat));
        manualWaves.add(List.of(MeleeRat, MeleeRat, RangedRat));
        manualWaves.add(List.of(FlockRat, FlockRat, FlockRat, RangedRat));
        manualWaves.add(List.of(FlockRat, FlockRat, FlockRat, RangedRat, RangedRat));
    }

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
        return 1;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return TimeUnit.SECONDS;
    }

    @Override
    public void run() {
        ArrayList<IEnemy> enemies = new ArrayList<>(getEnemies());

        //If enemies are missing, increment wave count
        if (gameData.isGameRunning() && world.getEntities(EEntityTypes.ENEMY).isEmpty()) {
            gameData.incrementWave();
            int wave = gameData.getCurrentWave();
            Random rand = new Random();

            //If Wave is not initialized, call wave set up
            if (!wavesInitialized) {
                manualWaves();
                wavesInitialized = true;
            }

            //If wave is less than manualWave's size, setup manual waves
            if (wave <= manualWaves.size()) {
                List<IEnemy> enemiesToSpawn = manualWaves.get(wave - 1);
                for (IEnemy enemy : enemiesToSpawn) {
                    Entity entity = enemy.createEnemy(gameData, world);
                    world.addEntity(entity);
                }
            } else {
                //After manual waves, spawn automatically
                for (int i = 0; i < wave; i++) {
                    int randNum = rand.nextInt(enemies.size());
                    Entity enemy = enemies.get(randNum).createEnemy(gameData, world);
                    world.addEntity(enemy);
                }
            }
        }
    }

    private Collection<? extends IEnemy> getEnemies() {
        return ServiceLoader.load(IEnemy.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }
}
