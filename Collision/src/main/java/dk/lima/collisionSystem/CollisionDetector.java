package dk.lima.collisionSystem;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.EEntityTypes;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.DamageCP;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.entitycomponents.ICollisionHandler;
import dk.lima.common.services.IPostEntityProcessingService;

import java.util.HashSet;
import java.util.List;

public class CollisionDetector implements IPostEntityProcessingService {
    private final List<EEntityTypes> invincibleTypes = List.of(EEntityTypes.COMPANION, EEntityTypes.HAZARD, EEntityTypes.OBSTACLE);
    private final HashSet<String> checkedEntities = new HashSet<>();

    public CollisionDetector() {
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (e.getEntityType() == EEntityTypes.HAZARD || e.getEntityType() == EEntityTypes.OBSTACLE) {
                continue;
            }

            TransformCP eTransformCP = (TransformCP) e.getComponent(EntityComponentTypes.TRANSFORM);
            if (eTransformCP == null) {
                continue;
            }

            checkedEntities.add(e.getID());

            Coordinate eCoord = eTransformCP.getCoord();

            for (Entity e2 : world.getEntities()) {
                if (checkedEntities.contains(e2.getID())) {
                    continue;
                }

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
                   if (e.getComponent(EntityComponentTypes.COLLISION) != null) {
                       ((ICollisionHandler) e.getComponent(EntityComponentTypes.COLLISION)).onCollide(e2, world);
                   }
                   if (e2.getComponent(EntityComponentTypes.COLLISION) != null) {
                        ((ICollisionHandler) e2.getComponent(EntityComponentTypes.COLLISION)).onCollide(e, world);
                   }
                }
            }
        }

        checkedEntities.clear();
    }
}
