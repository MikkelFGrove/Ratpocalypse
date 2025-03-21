package dk.lima.enemy.rangedrat;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.pathfinding.IPathfindingSPI;
import dk.lima.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class RangedRatProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(RangedRat.class)) {
            Coordinate start = new Coordinate(enemy.getX(), enemy.getY());
            Coordinate nextStep = new Coordinate(enemy.getX(), enemy.getY());
            if (getPathfindingSPI().stream().findFirst().isPresent() && world.getPlayerPosition() != null) {
                nextStep = getPathfindingSPI().stream().findFirst().get().calculateNextStep(start, world.getPlayerPosition());
            }

            enemy.setX(nextStep.getX());
            enemy.setY(nextStep.getY());

            double ratio = (nextStep.getY() - start.getY()) / (nextStep.getX() - start.getX());
            double angle = Math.toDegrees(Math.atan(ratio));

            // If difference is negative, add 180 to angle, to get correct angle
            if (nextStep.getX() - start.getX() < 0) {
                angle = 180 + angle;
            }

            Random random = new Random();
            if (random.nextInt(90) == 0) {
                RangedRat rat = (RangedRat) enemy;
                rat.getIWeaponSPI().shoot(enemy, gameData, world);
            }

            enemy.setRotation(angle);
        }
    }

    private Collection<? extends IPathfindingSPI> getPathfindingSPI() {
        return ServiceLoader.load(IPathfindingSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
