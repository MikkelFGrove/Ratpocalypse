package dk.lima.playersystem;

import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entity.IEntityComponent;
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

        double scale = 12;
        String[] pathsToSprites = {"player.png"};

        playerModel.addComponent(new PlayerCollisionHandler());
        playerModel.getComponent(EntityComponentTypes.COLLISION).setEntity(playerModel);
        for (IEntityComponent component : getEntityComponents()) {
            switch (component.getType()) {
                case SPRITE -> {
                    SpriteCP spriteCP = (SpriteCP) component;
                    spriteCP.setAmountOfSprites(pathsToSprites.length);
                    spriteCP.setPathsToSprite(pathsToSprites);
                    spriteCP.setHeight(gameData.tileSize);
                    spriteCP.setWidth(gameData.tileSize);
                    playerModel.addComponent(spriteCP);
                }
                case TRANSFORM -> {
                    TransformCP transformCP = (TransformCP) component;
                    transformCP.setCoord(world.getPlayerPosition());
                    transformCP.setRotation(0);
                    transformCP.setSize(scale);
                    playerModel.addComponent(transformCP);
                }
                case WEAPON -> {
                    WeaponCP weaponCP = (WeaponCP) component;
                    weaponCP.setEntity(playerModel);
                    weaponCP.setWeaponSPI(getWeaponSPI().stream().findFirst().orElse(null));
                    weaponCP.setAttackChance(1);
                    weaponCP.setShouldAttack(false);
                    playerModel.addComponent(weaponCP);
                }
                case HEALTH -> {
                    HealthCP healthCP = (HealthCP) component;
                    healthCP.setEntity(playerModel);
                    healthCP.setMaxHealth(100);
                    healthCP.setHealth(100);
                    playerModel.addComponent(healthCP);
                }
            }
        }
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

    public static Collection<? extends IEntityComponent> getEntityComponents() {
        return ServiceLoader.load(IEntityComponent.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
