package dk.lima.playersystem;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IGamePluginService;
import dk.lima.commonplayer.Player;

public class PlayerPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        Entity player = createPlayer(gameData);
        System.out.println(player);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        Entity playerModel = new Player();
        playerModel.setPolygonCoordinates(6.5*4, 3*4, 6*4, 3*4, 5.5*4, 2.5*4, 5.5*4, 2*4, 6*4, 1.5*4, 6.5*4, 1.5*4);
        playerModel.setX(gameData.getDisplayHeight()/2);
        playerModel.setY(gameData.getDisplayWidth()/2);
        playerModel.setRadius(8);
        playerModel.setRotation(0);

        return playerModel;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            world.removeEntity(player);
        }
    }
}
