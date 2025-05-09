package dk.lima.companion;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.pathfinding.IPathfinding;
import dk.lima.common.services.IEntityProcessingService;

import java.time.Duration;
import java.util.Random;

public class CompanionProcessor implements IEntityProcessingService {
    private static Duration timeSinceLastUpdate = null;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity companion : world.getEntities(Companion.class)) {
            for (IEntityComponent component : companion.getComponents()) {
                if (component instanceof IPathfinding pathfindingComponent) {
                    TransformCP transformCP = (TransformCP) companion.getComponent(EntityComponentTypes.TRANSFORM);
                    Coordinate companionCoord = transformCP.getCoord();

                    int radiusAroundPlayer = 100;
                    Coordinate playerCoord = world.getPlayerPosition();

                    double distance = Math.sqrt(
                            Math.pow(companionCoord.getX() - playerCoord.getX(), 2) +
                            Math.pow(companionCoord.getY() - playerCoord.getY(), 2)
                    );

                    if (distance > radiusAroundPlayer) {
                        pathfindingComponent.setLength(distance / 100);
                        pathfindingComponent.setTarget(world.getPlayerPosition());
                    } else {
                        if (timeSinceLastUpdate == null ||
                            gameData.getDuration().minus(timeSinceLastUpdate).compareTo(Duration.ofSeconds(3)) >= 0) {

                            timeSinceLastUpdate = gameData.getDuration();

                            Random rand = new Random();
                            double angle = 2 * Math.PI * rand.nextDouble();
                            double radius = radiusAroundPlayer * Math.sqrt(rand.nextDouble());
                            double offsetX = radius * Math.cos(angle);
                            double offsetY = radius * Math.sin(angle);

                            Coordinate randomCoord = new Coordinate(
                                    playerCoord.getX() + offsetX,
                                    playerCoord.getY() + offsetY
                            );

                            pathfindingComponent.setLength(0.75);
                            pathfindingComponent.setTarget(randomCoord);
                        }
                    }
                }
                component.process(gameData, world);
            }
        }
    }

    private Coordinate calculateStraightLineStep(Coordinate start, Coordinate goal, double length) {
        if (start.equals(goal)) {
            return start;
        }

        double xDistance = goal.getX() - start.getX();
        double yDistance = goal.getY() - start.getY();

        double hyp = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
        double scaledHyp = hyp / length;

        return new Coordinate(start.getX() + xDistance / scaledHyp, start.getY() + yDistance / scaledHyp);
    }
}
