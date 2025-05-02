package dk.lima.playersystem;

import dk.lima.common.data.*;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.entitycomponents.WeaponCP;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.player.Player;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player: world.getEntities(Player.class)) {
            TransformCP transformCP = (TransformCP) player.getComponent(EntityComponentTypes.TRANSFORM);
            WeaponCP weaponCP = (WeaponCP) player.getComponent(EntityComponentTypes.WEAPON);

            double x = gameData.getMousePosition().getX() - gameData.getDisplayWidth() / 2d;
            double y = gameData.getMousePosition().getY() - gameData.getDisplayHeight() / 2d;
            double angle = Math.atan2(y, x);
            transformCP.setRotation(Math.toDegrees(angle));

            Coordinate playerCoord = transformCP.getCoord();
            double velocity = 2.5;
            //Checks what input is registered and then either move, rotate or fires a bullet based on that.
            if (gameData.getInputs().isDown(EGameInputs.UP)) {
                //Updates the player's world position ensuring it can move, and keeps track on where the player is in the world.
                playerCoord.setY(playerCoord.getY() - velocity);
            }
            if (gameData.getInputs().isDown(EGameInputs.DOWN)) {
                //Updates the player's world position ensuring it can move, and keeps track on where the player is in the world.
                playerCoord.setY(playerCoord.getY() + velocity);
            }
            if (gameData.getInputs().isDown(EGameInputs.LEFT)) {
                //Updates the player's world position ensuring it can move, and keeps track on where the player is in the world.
                playerCoord.setX(playerCoord.getX() - velocity);
            }
            if (gameData.getInputs().isDown(EGameInputs.RIGHT)) {
                //Updates the player's world position ensuring it can move, and keeps track on where the player is in the world.
                playerCoord.setX(playerCoord.getX() + velocity);
            }
            world.setPlayerPosition(playerCoord);

            weaponCP.setShouldAttack(gameData.getInputs().isDown(EGameInputs.ACTION));
            weaponCP.process(gameData, world);

            HealthCP healthCP = (HealthCP) player.getComponent(EntityComponentTypes.HEALTH);
            healthCP.process(gameData, world);
        }
    }
}
