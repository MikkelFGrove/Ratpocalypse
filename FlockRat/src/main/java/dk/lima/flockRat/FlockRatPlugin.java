package dk.lima.flockRat;

import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.IEnemy;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.entitycomponents.*;
import dk.lima.common.services.IGamePluginService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class FlockRatPlugin implements IGamePluginService, IEnemy {
    @Override
    public void start(GameData gameData, World world) {}

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(FlockRat.class)){
            world.removeEntity(enemy);
        }
    }

    @Override
    public Entity createEnemy(GameData gameData, World world) {
        FlockRat enemy = new FlockRat();
        enemy.setEntityType(EEntityTypes.ENEMY);
        Random rnd = new Random();
        int scalingFactor = 6;

        String[] pathsToSprites = {"gang_rat.png"};

        double angle = rnd.nextDouble(0, 2 * Math.PI);
        double x = (Math.cos(angle) * gameData.getDisplayWidth() / 2) + world.getPlayerPosition().getX();
        double y = (Math.sin(angle) * gameData.getDisplayHeight() / 2) + world.getPlayerPosition().getY();

        Coordinate spawn = new Coordinate(x, y);

        while (!world.coordinateIsValid(spawn)) {
            angle = rnd.nextDouble(0, 2 * Math.PI);
            x = (Math.cos(angle) * gameData.getDisplayWidth() / 2) + world.getPlayerPosition().getX();
            y = (Math.sin(angle) * gameData.getDisplayHeight() / 2) + world.getPlayerPosition().getY();

            spawn = new Coordinate(x, y);
        }

        enemy.addComponent(new FlockRatCollisionHandler());
        enemy.getComponent(EntityComponentTypes.COLLISION).setEntity(enemy);

        for (IEntityComponent component : getEntityComponents()) {
            switch (component.getType()) {
                case PATHFINDING -> {
                    component.setEntity(enemy);
                    enemy.addComponent(component);
                }
                case FLOCK -> {
                    FlockCP flockCP = (FlockCP) component;
                    flockCP.setEntity(enemy);
                    enemy.addComponent(flockCP);
                }
                case SPRITE -> {
                    SpriteCP spriteCP = (SpriteCP) component;
                    spriteCP.setHeight(gameData.tileSize);
                    spriteCP.setWidth(gameData.tileSize);
                    spriteCP.setAmountOfSprites(pathsToSprites.length);
                    spriteCP.setPathsToSprite(pathsToSprites);
                    spriteCP.setLayer(1);
                    enemy.addComponent(spriteCP);
                }
                case TRANSFORM -> {
                    TransformCP transformCP = (TransformCP) component;
                    transformCP.setCoord(new Coordinate(x, y));
                    transformCP.setRotation(rnd.nextInt(90));
                    transformCP.setSize(2 * scalingFactor);
                    transformCP.setEntity(enemy);
                    enemy.addComponent(transformCP);
                }
                case HEALTH -> {
                    HealthCP healthCP = (HealthCP) component;
                    healthCP.setEntity(enemy);
                    healthCP.setMaxHealth(50);
                    healthCP.setHealth(50);
                    enemy.addComponent(healthCP);
                }
                case DAMAGE -> {
                    DamageCP damageCP = (DamageCP) component;
                    damageCP.setAttackDamage(0.75);
                    enemy.addComponent(damageCP);
                }
            }
        }
        return enemy;
    }

    public static Collection<? extends IEntityComponent> getEntityComponents() {
        return ServiceLoader.load(IEntityComponent.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
