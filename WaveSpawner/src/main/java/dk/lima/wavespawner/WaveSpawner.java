package dk.lima.wavespawner;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.IEnemy;
import dk.lima.common.services.IWaveSpawner;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.stream.Collectors;


public class WaveSpawner implements IWaveSpawner {

    private int waveNumber = 1;
    private int enemiesPerWave;


    private GameData gameData;
    private World world;

    public WaveSpawner(GameData gameData, World world, int startEnemies) {
        this.gameData = gameData;
        this.world = world;
        this.enemiesPerWave = startEnemies;
    }

    @Override
    public void update() {

    }

    private Collection<? extends IEnemy> getEnemies() {
        return ServiceLoader.load(IEnemy.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }


}
