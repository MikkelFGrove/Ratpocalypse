package dk.lima.enemy;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.Enemy;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.weapon.IWeaponSPI;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        Entity enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)){
            world.removeEntity(enemy);
        }
    }

    private Entity createEnemy(GameData gameData) {
        Enemy enemy = new Enemy();
        Random rnd = new Random();
        enemy.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemy.setX(gameData.getDisplayHeight()/3);
        enemy.setY(gameData.getDisplayWidth()/3);
        enemy.setRadius(5);
        enemy.setRotation(rnd.nextInt(90));
        getWeaponSPI().stream().findFirst().ifPresent(enemy::setIWeaponSPI);
        return enemy;
    }

    private Collection<? extends IWeaponSPI> getWeaponSPI() {
        return ServiceLoader.load(IWeaponSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
