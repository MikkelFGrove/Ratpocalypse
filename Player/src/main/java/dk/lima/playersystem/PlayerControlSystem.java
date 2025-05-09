package dk.lima.playersystem;

import dk.lima.common.data.*;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entitycomponents.MovementCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.entitycomponents.WeaponCP;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.player.Player;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for(Entity player : world.getEntities(Player.class)) {

            WeaponCP weaponCP = (WeaponCP) player.getComponent(EntityComponentTypes.WEAPON);

            weaponCP.setShouldAttack(gameData.getInputs().isDown(EGameInputs.ACTION));
            weaponCP.process(gameData, world);

            HealthCP healthCP = (HealthCP) player.getComponent(EntityComponentTypes.HEALTH);
            healthCP.process(gameData, world);

            MovementCP movementCP = (MovementCP) player.getComponent(EntityComponentTypes.MOVEMENT);
        }
    }
}
