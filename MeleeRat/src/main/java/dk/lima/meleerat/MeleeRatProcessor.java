package dk.lima.meleerat;

import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.pathfinding.IPathfindingSPI;
import dk.lima.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class MeleeRatProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(MeleeRat.class)) {
            TransformCP transformCP = enemy.getComponent(TransformCP.class);

            Coordinate start = transformCP.getCoord();
            Coordinate nextStep = transformCP.getCoord();
            if (getPathfindingSPI().stream().findFirst().isPresent() && world.getPlayerPosition() != null) {
                nextStep = getPathfindingSPI().stream().findFirst().get().calculateNextStep(start, world.getPlayerPosition());
            }

            transformCP.setCoord(nextStep);

            double ratio = (nextStep.getY() - start.getY()) / (nextStep.getX() - start.getX());
            double angle = Math.toDegrees(Math.atan(ratio));

            // If difference is negative, add 180 to angle, to get correct angle
            if (nextStep.getX() - start.getX() < 0) {
                angle = 180 + angle;
            }

            transformCP.setRotation(angle);
        }
    }

    private Collection<? extends IPathfindingSPI> getPathfindingSPI() {
        return ServiceLoader.load(IPathfindingSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
