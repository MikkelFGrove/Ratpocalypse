package dk.lima.enemy;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.Enemy;
import dk.lima.common.pathfinding.IPathfindingSPI;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.weapon.IWeaponSPI;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {

            Coordinate start = new Coordinate(enemy.getPosition().getX(), enemy.getPosition().getY());
            Coordinate nextStep = new Coordinate(enemy.getPosition().getX(), enemy.getPosition().getY());
            if (getPathfindingSPI().stream().findFirst().isPresent() && world.getPlayerPosition() != null) {

                nextStep = getPathfindingSPI().stream().findFirst().get().calculateNextStep(start, world.getPlayerPosition());

            }

            enemy.setPosition(nextStep);

            double ratio = (nextStep.getY() - start.getY()) / (nextStep.getX() - start.getX());
            double angle = Math.toDegrees(Math.atan(ratio));

            // If difference is negative, add 180 to angle, to get correct angle
            if (nextStep.getX() - start.getX() < 0) {
                angle = 180 + angle;
            }

            enemy.setRotation(angle);
        }
    }

    private Collection<? extends IPathfindingSPI> getPathfindingSPI() {
        return ServiceLoader.load(IPathfindingSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
