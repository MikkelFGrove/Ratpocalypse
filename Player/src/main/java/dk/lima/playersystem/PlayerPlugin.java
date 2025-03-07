package dk.lima.playersystem;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.weapon.IWeaponSPI;
import dk.lima.commonplayer.Player;

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
        playerModel.setPolygonCoordinates(6.5*4, 3*4, 6*4, 3*8, 5.5*4, 2.5*4, 5.5*4, 2*4, 6*4, 1.5*4, 6.5*4, 1.5*4);
        playerModel.setX(gameData.getDisplayHeight()/2);
        playerModel.setY(gameData.getDisplayWidth()/2);
        playerModel.setRadius(8);
        playerModel.setRotation(0);

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
