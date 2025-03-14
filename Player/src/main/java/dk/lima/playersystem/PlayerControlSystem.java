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

            double velocity = 1;
            double rotationSpeed = 3;

            if (gameData.getInputs().isDown(EGameInputs.UP)) {
                player.setX(player.getX() + changeX * velocity);
                player.setY(player.getY() + changeY * velocity);
            }
            if (gameData.getInputs().isDown(EGameInputs.DOWN)) {
                player.setX(player.getX() - changeX * velocity);
                player.setY(player.getY() - changeY * velocity);
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

            if (player.getX() < 0) {
                player.setX(gameData.getDisplayWidth());
            } else if (player.getX() > gameData.getDisplayWidth()) {
                player.setX(0);
            }

            if (player.getY() < 0) {
                player.setY(gameData.getDisplayHeight());
            } else if (player.getY() > gameData.getDisplayHeight()) {
                player.setY(0);
            }

            Coordinate playerPosition = new Coordinate(player.getX(), player.getY());
            world.setPlayerPosition(playerPosition);
        }
    }
}
