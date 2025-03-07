package dk.lima.playersystem;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IGamePluginService;
import dk.lima.commonplayer.Player;

public class PlayerPlugin implements IGamePluginService {

    int scale = 8;

    @Override
    public void start(GameData gameData, World world) {
        Entity player = createPlayer(gameData);
        System.out.println(player);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        Entity playerModel = new Player();

        double[] baseCoordinates = {
                5, 2,
                5, 3,
                5, 6,
                3, 4,
                7, 4,
                5, 6,
                4, 9,
                6, 9,
                5, 6
        };

        for (int i = 0; i < baseCoordinates.length; i++) {
            baseCoordinates[i] = baseCoordinates[i] * scale;
        }

        playerModel.setX(gameData.getDisplayHeight()/2);
        playerModel.setY(gameData.getDisplayWidth()/2);
        playerModel.setRadius(8);
        playerModel.setRotation(0);
        playerModel.setColor(new int[]{255, 0, 255});
        playerModel.setPolygonCoordinates(baseCoordinates);
        return playerModel;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            world.removeEntity(player);
        }
    }
}
