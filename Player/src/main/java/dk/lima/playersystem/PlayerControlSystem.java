package dk.lima.playersystem;

import dk.lima.common.data.*;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.entitycomponents.WeaponCP;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.player.Player;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player: world.getEntities(Player.class)) {
            double velocity = 1.5;

            TransformCP transformCP = (TransformCP) player.getComponent(EntityComponentTypes.TRANSFORM);
            WeaponCP weaponCP = (WeaponCP) player.getComponent(EntityComponentTypes.WEAPON);
            Coordinate coord = transformCP.getCoord();

            double x = gameData.getMousePosition().getX() - ((double) gameData.getDisplayWidth() / 2);
            double y = gameData.getMousePosition().getY() - ((double) gameData.getDisplayHeight() / 2);
            if (world.getPlayerPosition() == null){
                world.setPlayerPosition(new Coordinate(x,y));
            }

            double angle = Math.atan2(y, x);

            transformCP.setRotation(Math.toDegrees(angle));


            //Checks what input is registered and then either move, rotate or fires a bullet based on that.
            if (gameData.getInputs().isDown(EGameInputs.UP)) {

                //Updates the player's world position ensuring it can move, and keeps track on where the player is in the world.
                world.setPlayerY(world.getPlayerY() + velocity);

                //This ensures that as the player moves around the map, the camera gets adjusted to keep them centered.
                // Other components (collision, weapons, etc.) depends on these coordinates, to work properly.
                coord.setY(-world.getPlayerY() + gameData.getDisplayHeight() / 2d);
            }
            if (gameData.getInputs().isDown(EGameInputs.DOWN)) {
                //Updates the player's world position ensuring it can move, and keeps track on where the player is in the world.
                world.setPlayerY(world.getPlayerY() - velocity);

                //This ensures that as the player moves around the map, the camera gets adjusted to keep them centered.
                // Other components (collision, weapons, etc.) depends on these coordinates, to work properly.
                coord.setY(-world.getPlayerY() + gameData.getDisplayHeight() / 2d);
            }
            if (gameData.getInputs().isDown(EGameInputs.LEFT)) {
                //Updates the player's world position ensuring it can move, and keeps track on where the player is in the world.
                world.setPlayerX(world.getPlayerX() + velocity);

                //This ensures that as the player moves around the map, the camera gets adjusted to keep them centered.
                // Other components (collision, weapons, etc.) depends on these coordinates, to work properly.
                coord.setX(-world.getPlayerX() + gameData.getDisplayWidth() / 2d);
            }
            if (gameData.getInputs().isDown(EGameInputs.RIGHT)) {
                //Updates the player's world position ensuring it can move, and keeps track on where the player is in the world.
                world.setPlayerX(world.getPlayerX() - velocity);

                //This ensures that as the player moves around the map, the camera gets adjusted to keep them centered.
                // Other components (collision, weapons, etc.) depends on these coordinates, to work properly.
                coord.setX(-world.getPlayerX() + gameData.getDisplayWidth() / 2d);
            }

            weaponCP.setShouldAttack(false);
            if (gameData.getInputs().isDown(EGameInputs.ACTION)) {
                weaponCP.setShouldAttack(true);
            }
            weaponCP.process(gameData, world);


            Coordinate playerPosition = new Coordinate(coord.getX(), coord.getY());
            world.setPlayerPosition(playerPosition);
        }
    }
}
