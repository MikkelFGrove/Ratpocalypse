package dk.lima.enemy.rangedrat;

import dk.lima.common.data.*;
import dk.lima.common.services.IEntityProcessingService;
import java.util.Random;


public class RangedRatProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(RangedRat.class)) {
            for (IEntityComponent component : enemy.getComponents()) {
                component.process(gameData, world);
            }
        }
    }
}
