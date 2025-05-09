package dk.lima.playersystem;

import dk.lima.common.data.*;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.player.Player;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for(Entity player : world.getEntities(Player.class)) {
            for (IEntityComponent component: player.getComponents()) {
                component.process(gameData, world);
            }
        }
    }
}
