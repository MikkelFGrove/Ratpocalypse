package dk.lima.playersystem;

import dk.lima.common.entity.Entity;
import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entitycomponents.SpriteCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.entitycomponents.WeaponCP;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.weapon.IWeaponSPI;
import dk.lima.common.player.Player;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        Entity player = createPlayer(gameData, world);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gameData, World world) {
        Player playerModel = new Player();
        playerModel.setEntityType(EEntityTypes.PLAYER);

        double scale = 7.5;

        playerModel.addComponent(new TransformCP(
                world.getPlayerPosition(),
                0,
                scale
        ));

        String[] pathsToSprites = {"player.png"};
        playerModel.addComponent(new SpriteCP(
                pathsToSprites,
                pathsToSprites.length,
                3
        ));

        playerModel.addComponent(new WeaponCP(
                playerModel,
                getWeaponSPI().stream().findFirst().orElse(null),
                1,
                100,
                false
        ));

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
