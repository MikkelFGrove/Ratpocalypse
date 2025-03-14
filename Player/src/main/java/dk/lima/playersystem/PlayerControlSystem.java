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

            if (gameData.getInputs().isDown(EGameInputs.UP)) {
                world.setPlayerX(world.getPlayerX() - changeX * velocity);
                world.setPlayerY(world.getPlayerY() - changeY * velocity);
                player.setX(-world.getPlayerX() + gameData.getDisplayWidth() / 2);
                player.setY(-world.getPlayerY() + gameData.getDisplayHeight() / 2);
            }
            if (gameData.getInputs().isDown(EGameInputs.DOWN)) {
                world.setPlayerX(world.getPlayerX() + changeX * velocity);
                world.setPlayerY(world.getPlayerY() + changeY * velocity);
                player.setX(-world.getPlayerX() + gameData.getDisplayWidth() / 2);
                player.setY(-world.getPlayerY() + gameData.getDisplayHeight() / 2);
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

            Coordinate playerPosition = new Coordinate(player.getX(), player.getY());
            world.setPlayerPosition(playerPosition);
        }
    }
}
