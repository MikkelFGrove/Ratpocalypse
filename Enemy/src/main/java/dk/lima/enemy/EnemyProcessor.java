package dk.lima.enemy;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.Enemy;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.weapon.IWeaponSPI;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyProcessor implements IEntityProcessingService {


    @Override
    public void process(GameData gameData, World world) {


        Random rnd = new Random();
        for (Entity enemy : world.getEntities(Enemy.class)) {
            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));


            enemy.setX(enemy.getX() + changeX * 0.5);
            enemy.setY(enemy.getY() + changeY * 0.5);

            if (enemy.getX() < 0) {
                enemy.setX(enemy.getX() - gameData.getDisplayWidth());
            }

            if (enemy.getX() > gameData.getDisplayWidth()) {
                enemy.setX(enemy.getX() % gameData.getDisplayWidth());
            }

            if (enemy.getY() < 0) {
                enemy.setY(enemy.getY() - gameData.getDisplayHeight());
            }

            if (enemy.getY() > gameData.getDisplayHeight()) {
                enemy.setY(enemy.getY() % gameData.getDisplayHeight());
            }
            if (rnd.nextInt(90) < 2){
                enemy.setRotation(-20);
            }
            if (rnd.nextInt(90) < 1){
                enemy.setRotation(15);
            }
            if (rnd.nextInt(90) < 2){
                Enemy e = (Enemy) enemy;
                e.getIWeaponSPI().shoot(enemy, gameData, world);
            }
        }
    }
}
