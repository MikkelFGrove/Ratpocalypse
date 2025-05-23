package dk.lima.companion;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.entitycomponents.SpriteCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.services.IGamePluginService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class CompanionPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        Entity companion = createCompanion(gameData, world);
        world.addEntity(companion);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Companion.class)){
            world.removeEntity(enemy);
        }
    }

    private Entity createCompanion(GameData gameData, World world) {
        Companion companion = new Companion();
        companion.setEntityType(EEntityTypes.COMPANION);
        int scalingFactor = 6;

        String[] pathsToSprites = {"turtle.png"};
        double x = world.getPlayerPosition().getX() + 30;
        double y = world.getPlayerPosition().getY() + 30;

        for (IEntityComponent component : getEntityComponents()) {
            switch (component.getType()) {
                case SPRITE -> {
                    SpriteCP spriteCP = (SpriteCP) component;
                    spriteCP.setHeight(gameData.getTileSize());
                    spriteCP.setWidth(gameData.getTileSize());
                    spriteCP.setAmountOfSprites(pathsToSprites.length);
                    spriteCP.setPathsToSprite(pathsToSprites);
                    spriteCP.setLayer(1);
                    companion.addComponent(spriteCP);
                }
                case PATHFINDING -> {
                    component.setEntity(companion);
                    companion.addComponent(component);
                }
                case TRANSFORM -> {
                    TransformCP transformCP = (TransformCP) component;
                    transformCP.setCoord(new Coordinate(x, y));
                    transformCP.setRotation(0);
                    transformCP.setSize(2 * scalingFactor);
                    transformCP.setEntity(companion);
                    companion.addComponent(transformCP);
                }

            }
        }
        return companion;

    }

    private static Collection<? extends IEntityComponent> getEntityComponents() {
        return ServiceLoader.load(IEntityComponent.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
