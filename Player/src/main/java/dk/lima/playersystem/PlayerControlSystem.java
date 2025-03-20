package dk.lima.playersystem;

import dk.lima.common.data.*;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.player.Player;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player: world.getEntities(Player.class)) {
            double x = gameData.getMousePosition().getX() - ((double) gameData.getDisplayWidth() / 2);
            double y = gameData.getMousePosition().getY() - ((double) gameData.getDisplayHeight() / 2);
            double angle = Math.atan2(y, x);
            player.setRotation(Math.toDegrees(angle));

            double velocity = 1.5;
            if (gameData.getInputs().isDown(EGameInputs.UP)) {
                world.setPlayerY(world.getPlayerY() + velocity);
                player.setY(-world.getPlayerY() + gameData.getDisplayHeight() / 2);
            }
            if (gameData.getInputs().isDown(EGameInputs.DOWN)) {
                world.setPlayerY(world.getPlayerY() - velocity);
                player.setY(-world.getPlayerY() + gameData.getDisplayHeight() / 2);
            }
            if (gameData.getInputs().isDown(EGameInputs.LEFT)) {
                world.setPlayerX(world.getPlayerX() + velocity);
                player.setX(-world.getPlayerX() + gameData.getDisplayWidth() / 2);
            }
            if (gameData.getInputs().isDown(EGameInputs.RIGHT)) {
                world.setPlayerX(world.getPlayerX() - velocity);
                player.setX(-world.getPlayerX() + gameData.getDisplayWidth() / 2);
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
