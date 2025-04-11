package dk.lima.companion;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.services.IEntityProcessingService;

public class CompanionProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Companion.class)) {
            for (IEntityComponent component : enemy.getComponents()) {
                component.process(gameData, world);
            }
        }
    }
}
