package dk.lima.wavespawner;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.IEnemy;
import dk.lima.common.services.IWaveSpawner;


import java.util.Collection;
import java.util.ServiceLoader;
import java.util.stream.Collectors;


public class WaveSpawner implements IWaveSpawner {


    @Override
    public void update(GameData gameData, World world) {
        if (gameData.getDuration().toSeconds() % 5 == 0) {
            getEnemies().stream().findFirst().ifPresent(iEnemy -> {world.addEntity(iEnemy.createEnemy(gameData));});
        }

    }

    private Collection<? extends IEnemy> getEnemies() {
        return ServiceLoader.load(IEnemy.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }


}
