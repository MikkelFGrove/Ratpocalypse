package dk.lima.enemy.rangedrat;

import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.EntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.IEnemy;
import dk.lima.common.entitycomponents.ShapeCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.entitycomponents.WeaponCP;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.weapon.IWeaponSPI;
import dk.lima.pathfindingComponent.PathfindingComponent;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class RangedRatPlugin implements IGamePluginService, IEnemy {
    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < 3; i++) {
            Entity enemy = createEnemy(gameData, world);
            enemy.addComponent(new PathfindingComponent(enemy));
            world.addEntity(enemy);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(RangedRat.class)){
            world.removeEntity(enemy);
        }
    }

    @Override
    public Entity createEnemy(GameData gameData, World world) {
        RangedRat enemy = new RangedRat();
        enemy.setEntityType(EntityTypes.ENEMY);
        Random rnd = new Random();
        int scalingFactor = 6;
        double[] polygonCoordinates = {-0.02, 1.13, -0.78, 1.04, -1.4, 0.75, -1.92, 0.34, -2.25, -0.23, -2.42, -0.8, -2.65, -1.17, -3.02, -1.5, -3.46, -1.67, -3.98, -1.79, -4.54, -1.79, -4.91, -1.63, -4.96, -1.22, -4.95, -0.91, -5.09, -1.17, -5.12, -1.43, -5, -1.69, -4.58, -1.93, -3.97, -1.93, -3.4, -1.85, -2.91, -1.68, -2.43, -1.34, -2.2, -1, -1.92, -1.24, -1.91, -1.33, -2.2, -1.55, -2.14, -1.87, -1.77, -1.99, -1.5, -1.79, -1.62, -1.7, -2.01, -1.66, -1.28, -1.33,-0.75, -1.37, -0.87, -1.51, -0.8, -1.8, -0.14, -1.86, -0.01, -1.7, -0.63, -1.57, -0.28, -1.12, 0.41, -0.9, 0.94, -1.77, 1.71, -1.83, 1.71, -1.64, 1.22, -1.55, 1.01, -1.26, 1.3, -0.83, 1.62, -1.1, 1.72, -1.33, 2.19, -1.49, 2.43, -1.41, 2.2, -1.2, 1.9, -1.2, 1.74, -0.96, 1.9, -0.39, 2.64, 0.06, 3.76, 0.24, 4, 0.5, 4.08, 0.71, 3.01, 1.42, 2.43, 1.42, 2.25, 1.57, 1.99, 1.57, 1.89, 1.39, 1.91, 1.09, 1.38, 0.94};
        for (int i = 0; i < polygonCoordinates.length; i++) {
            polygonCoordinates[i] *= scalingFactor;
        }

        enemy.addComponent(new ShapeCP(
                polygonCoordinates,
                new int[]{250, 17, 68}
        ));

        double angle = rnd.nextDouble(0, 2 * Math.PI);
        double x = (Math.cos(angle) * gameData.getDisplayWidth() / 2) + gameData.getDisplayWidth() / 2d - world.getPlayerX();
        double y = (Math.sin(angle) * gameData.getDisplayHeight() / 2) + gameData.getDisplayHeight() / 2d - world.getPlayerY();

        enemy.addComponent(new PathfindingComponent(enemy));
        enemy.addComponent(new TransformCP(
                new Coordinate(x, y),
                rnd.nextInt(90),
                2 * scalingFactor
        ));


        enemy.addComponent(new WeaponCP(
                enemy,
                getWeaponSPI().stream().findFirst().orElse(null),
                90,
                100,
                true
        ));


        return enemy;
    }

    private Collection<? extends IWeaponSPI> getWeaponSPI() {
        return ServiceLoader.load(IWeaponSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
