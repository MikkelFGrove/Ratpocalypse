package dk.lima.playersystem;

import dk.lima.common.data.*;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.player.Player;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player: world.getEntities(Player.class)) {
            double angle = Math.toRadians(player.getRotation());
            double changeX = Math.cos(angle);
            double changeY = Math.sin(angle);

            double velocity = 2;
            double rotationSpeed = 3;

            // Get the player's world position
            Coordinate worldPosition = world.getPlayerPosition();
            if (worldPosition == null) {
                worldPosition = new Coordinate(0, 0); // Default starting position
                world.setPlayerPosition(worldPosition);
            }

            if (gameData.getInputs().isDown(EGameInputs.UP)) {
                worldPosition = new Coordinate(worldPosition.getX() - changeX * velocity,
                        worldPosition.getY() - changeY * velocity);
            }
            if (gameData.getInputs().isDown(EGameInputs.DOWN)) {
                worldPosition = new Coordinate(worldPosition.getX() + changeX * velocity,
                        worldPosition.getY() + changeY * velocity);
            }
            if (gameData.getInputs().isDown(EGameInputs.LEFT)) {
                player.setRotation(player.getRotation() - rotationSpeed);
            }
            if (gameData.getInputs().isDown(EGameInputs.RIGHT)) {
                player.setRotation(player.getRotation() + rotationSpeed);
            }
            if (gameData.getInputs().isDown(EGameInputs.ACTION)) {
                Player p = (Player) player;
                p.getIWeaponSPI().shoot(player, gameData, world);
            }

            // Update the world position
            world.setPlayerPosition(worldPosition);

            // Convert world position to screen position
            Coordinate screenPosition = new Coordinate(
                    -worldPosition.getX() + gameData.getDisplayWidth() / 2.0,
                    -worldPosition.getY() + gameData.getDisplayHeight() / 2.0
            );

            // Update the player position on screen
            player.setPosition(screenPosition);
        }
    }
}
