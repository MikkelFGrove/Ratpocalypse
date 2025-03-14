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
                world.setPlayerPosition(new Coordinate(world.getPlayerPosition().getX()- changeX * velocity,
                        world.getPlayerPosition().getY() - changeY * velocity) );

                player.setPosition(new Coordinate(-world.getPlayerPosition().getX() + gameData.getDisplayWidth() / 2,
                        -world.getPlayerPosition().getY() + gameData.getDisplayHeight() / 2));

            }
            if (gameData.getInputs().isDown(EGameInputs.DOWN)) {
                world.setPlayerPosition(new Coordinate(world.getPlayerPosition().getX() + changeX * velocity,
                        world.getPlayerPosition().getY() + changeY * velocity) );

                player.setPosition(new Coordinate(-world.getPlayerPosition().getX() + gameData.getDisplayWidth() / 2,
                        -world.getPlayerPosition().getY() + gameData.getDisplayHeight() / 2));
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

            Coordinate playerPosition = new Coordinate(player.getPosition().getX(), player.getPosition().getY());
            world.setPlayerPosition(playerPosition);
        }
    }
}
