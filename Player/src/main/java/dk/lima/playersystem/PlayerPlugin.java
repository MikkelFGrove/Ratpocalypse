package dk.lima.playersystem;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.weapon.IWeaponSPI;
import dk.lima.common.player.Player;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerPlugin implements IGamePluginService {

    int scale = 8;

    @Override
    public void start(GameData gameData, World world) {
        Entity player = createPlayer(gameData);
        System.out.println(player);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData) {
        Player playerModel = new Player();

        double[] baseCoordinates = {
                0.0, -3.44,
                0.0, -2.44,
                0.0, 0.56,
                -2.0, -1.44,
                2.0, -1.44,
                0.0, 0.56,
                -1.0, 3.56,
                1.0, 3.56,
                0.0, 0.56
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
