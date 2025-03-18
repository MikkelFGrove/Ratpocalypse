package dk.lima.playersystem;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.weapon.IWeaponSPI;
import dk.lima.common.player.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        Entity player = createPlayer(gameData);
        System.out.println(player);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        Player playerModel = new Player();

        double scale = 7.5;
        double[] polygonCoordinates = {2.0, 0.95, 2.01, 0.65, 1.41, 0.64, 1.41, 0.7, 0.41, 0.69, 0.35, 0.4, 0.21, 0.2, -0.07, 0.08, -0.26, -0.12, -0.43, -0.39, -0.48, -0.74, -0.7, -0.96, -1.067, -1.04, -1.28, -1.3, -1.61, -1.37, -1.95, -1.24, -2.07, -0.89, -2.53, -0.6, -2.74, -0.44, -2.79, 1.09, -2.66, 1.23, -2.65, 1.47, -2.57, 1.55, -2.11, 1.56, -2.01, 1.9, -1.72, 2.07, -0.4, 2.02, -0.81, 1.64, -0.54, 1.5, -0.41, 1.37, -0.32, 1.18, 0.12, 1.19, 0.12, 1.1, 1.4, 1.12, 1.4, 1.21, 1.98, 1.22};
        
        for (int i = 0; i < polygonCoordinates.length; i++) {
            polygonCoordinates[i] *= scale;
        }

        playerModel. setX(gameData.getDisplayHeight() / 2);
        playerModel.setY(gameData.getDisplayWidth() / 2);
        playerModel.setRadius((float) scale);
        playerModel.setRotation(0);
        playerModel.setColor(new int[]{255, 0, 255});
        playerModel.setPolygonCoordinates(polygonCoordinates);

        getWeaponSPI().stream().findFirst().ifPresent(playerModel::setIWeaponSPI);

        return playerModel;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            world.removeEntity(player);
        }
    }

    private Collection<? extends IWeaponSPI> getWeaponSPI() {
        return ServiceLoader.load(IWeaponSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
