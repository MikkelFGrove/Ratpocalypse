package dk.lima.enemy.rangedrat;

import dk.lima.common.entity.Entity;
import dk.lima.common.data.*;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.pathfinding.IPathfinding;
import dk.lima.common.services.IEntityProcessingService;


public class RangedRatProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(RangedRat.class)) {
            for (IEntityComponent component : enemy.getComponents()) {
                if (component instanceof IPathfinding) {
                    ((IPathfinding) component).setTarget(world.getPlayerPosition());
                }
                component.process(gameData, world);
            }
        }
    }
}
