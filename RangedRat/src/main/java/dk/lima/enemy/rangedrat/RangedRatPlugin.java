package dk.lima.enemy.rangedrat;

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
import dk.lima.common.weapon.IWeaponSPI;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class RangedRatPlugin implements IGamePluginService, IEnemy {
    @Override
    public void start(GameData gameData, World world) {}

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(RangedRat.class)){
            world.removeEntity(enemy);
        }
    }

    @Override
    public Entity createEnemy(GameData gameData, Coordinate coordinate) {
        RangedRat enemy = new RangedRat();
        enemy.setEntityType(EEntityTypes.ENEMY);
        Random rnd = new Random();

        String[] pathsToSprites = {"soldier_rat.png"};

        enemy.addComponent(new RangedRatCollisionHandler());
        enemy.getComponent(EntityComponentTypes.COLLISION).setEntity(enemy);

        for (IEntityComponent component : getEntityComponents()) {
            switch (component.getType()) {
                case PATHFINDING -> {
                    component.setEntity(enemy);
                    enemy.addComponent(component);
                }
                case SPRITE -> {
                    SpriteCP spriteCP = (SpriteCP) component;
                    spriteCP.setAmountOfSprites(pathsToSprites.length);
                    spriteCP.setPathsToSprite(pathsToSprites);
                    spriteCP.setHeight(gameData.getTileSize());
                    spriteCP.setWidth(gameData.getTileSize());
                    spriteCP.setLayer(1);
                    enemy.addComponent(spriteCP);
                }
                case TRANSFORM -> {
                    TransformCP transformCP = (TransformCP) component;
                    transformCP.setCoord(coordinate);
                    transformCP.setRotation(rnd.nextInt(90));
                    transformCP.setSize(15);
                    enemy.addComponent(transformCP);
                }
                case WEAPON -> {
                    WeaponCP weaponCP = (WeaponCP) component;
                    weaponCP.setEntity(enemy);
                    weaponCP.setWeaponSPI(getWeaponSPI().stream().findFirst().orElse(null));
                    weaponCP.setAttackChance(90);
                    weaponCP.setShouldAttack(true);
                    enemy.addComponent(weaponCP);
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
                    damageCP.setAttackDamage(0.4);
                    enemy.addComponent(damageCP);
                }

            }
        }
        return enemy;
    }

    private Collection<? extends IWeaponSPI> getWeaponSPI() {
        return ServiceLoader.load(IWeaponSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    public static Collection<? extends IEntityComponent> getEntityComponents() {
        return ServiceLoader.load(IEntityComponent.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
