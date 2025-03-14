package dk.lima.enemy;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.Enemy;
import dk.lima.common.enemy.IEnemy;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.weapon.IWeaponSPI;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyPlugin implements IGamePluginService, IEnemy {
    @Override
    public void start(GameData gameData, World world) {
        Entity enemy1 = createEnemy(gameData);
        Entity enemy2 = createEnemy(gameData);
        Entity enemy3 = createEnemy(gameData);
        enemy1.setX(100);
        enemy1.setY(100);
        enemy2.setX(200);
        enemy2.setY(200);
        enemy3.setX(700);
        enemy3.setY(700);
        world.addEntity(enemy1);
        world.addEntity(enemy2);
        world.addEntity(enemy3);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)){
            world.removeEntity(enemy);
        }
    }

    public Entity createEnemy(GameData gameData) {
        Enemy enemy = new Enemy();
        Random rnd = new Random();
        enemy.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemy.setX(rnd.nextInt(gameData.getDisplayWidth()));
        enemy.setY(rnd.nextInt(gameData.getDisplayHeight()));
        enemy.setRadius(5);
        enemy.setRotation(rnd.nextInt(90));
        getWeaponSPI().stream().findFirst().ifPresent(enemy::setIWeaponSPI);
        return enemy;
    }

    private Collection<? extends IWeaponSPI> getWeaponSPI() {
        return ServiceLoader.load(IWeaponSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
