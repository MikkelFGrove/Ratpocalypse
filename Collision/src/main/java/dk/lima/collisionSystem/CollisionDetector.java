package dk.lima.collisionSystem;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.EEntityTypes;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {

    public CollisionDetector() {
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {

            TransformCP eTransformCP = (TransformCP) e.getComponent(EntityComponentTypes.TRANSFORM);
            if (eTransformCP == null) {
                continue;
            }
            Coordinate eCoord = eTransformCP.getCoord();

            for (Entity e2 : world.getEntities()) {
                // Make sure entities are different. Cannot collide with oneself
                if (e.getID().equals(e2.getID())){
                    continue;
                }
                //If entities are of the same type, they will  not kill each other by collision
                if (e.getEntityType() != null && e2.getEntityType() != null && e.getEntityType().equals(e2.getEntityType())) {
                    continue;
                }

                TransformCP e2TransformCP = (TransformCP) e2.getComponent(EntityComponentTypes.TRANSFORM);
                if (e2TransformCP == null) {
                    continue;
                }

                Coordinate e2Coord = e2TransformCP.getCoord();

                // Using Pythagoras' distance formula
                double xDistance = eCoord.getX() - e2Coord.getX();
                double yDistance = eCoord.getY() - e2Coord.getY();
                double distance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

                if (distance < (eTransformCP.getSize() + e2TransformCP.getSize())) {
                    // Increase score if an enemy is shot
                    if (!gameData.isTimeScoring() && ((e.getEntityType() == EEntityTypes.BULLET && e2.getEntityType() == EEntityTypes.ENEMY) || (e.getEntityType() == EEntityTypes.ENEMY && e2.getEntityType() == EEntityTypes.BULLET))) {
                        gameData.setScore(gameData.getScore() + 1);
                    }
                    if (e.getEntityType() != EEntityTypes.COMPANION && e2.getEntityType() != EEntityTypes.COMPANION) {
                        world.removeEntity(e);
                        world.removeEntity(e2);
                    }

                }
            }
        }
    }
}
