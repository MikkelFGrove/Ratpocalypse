package dk.lima.flockRat;

import dk.lima.common.data.*;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.services.IEntityProcessingService;


public class FlockRatProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(FlockRat.class)) {
            for (IEntityComponent component : enemy.getComponents()) {
                component.process(gameData, world);
            }
        }
    }
}
