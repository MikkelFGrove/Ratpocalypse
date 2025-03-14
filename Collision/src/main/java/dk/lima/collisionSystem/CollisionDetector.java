package dk.lima.collisionSystem;


import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {

    public CollisionDetector() {
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            for (Entity e2 : world.getEntities()) {
                // Make sure entities are different. Cannot collide with oneself
                if (e.getID().equals(e2.getID())) {
                    continue;
                }

                // Using Pythagoras' distance formula
                double xDistance = e.getPosition().getX() - e2.getPosition().getX();
                double yDistance = e.getPosition().getY() - e2.getPosition().getY();
                double distance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

                if (distance < (e.getRadius() + e2.getRadius())) {
                    world.removeEntity(e);
                    world.removeEntity(e2);
                }
            }
        }
    }
}
