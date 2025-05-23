package dk.lima.collisionSystem;

import dk.lima.bullet.BulletGamePlugin;
import dk.lima.common.bullet.Bullet;
import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.DamageCP;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.obstacle.Obstacle;
import dk.lima.playersystem.PlayerCollisionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {
    private static GameData gameData;
    private static World world;
    private static CollisionDetector collisionDetector;

    @BeforeAll
    public static void setup() {
        gameData = new GameData();
        world = new World();
        collisionDetector = new CollisionDetector();
    }

    @AfterEach
    public void removeEntities() {
        for (Entity e : world.getEntities()) {
            world.removeEntity(e);
        }
    }

    @AfterEach
    public void resetScore() {
        gameData.setScore(0);
    }

    @Test
    public void testBulletCollisionRemovesBullet() {
        BulletGamePlugin bulletGamePlugin = new BulletGamePlugin();
        Bullet bullet = bulletGamePlugin.createBullet(0,0,0,1,1);

        Entity entity = new Entity();
        //bullet's coordinates are changed in create bullet. The coordinates below reflect that change

        entity.addComponent(new TransformCP(new Coordinate(2.5,0),0,1));
        DamageCP dmCP = new DamageCP();
        dmCP.setAttackDamage(2);
        entity.addComponent(dmCP);

        world.addEntity(bullet);
        world.addEntity(entity);

        assertTrue(world.getEntities().contains(entity));
        assertTrue(world.getEntities().contains(bullet));

        collisionDetector.process(gameData, world);

        // Check that the bullet is removed
        assertFalse(world.getEntities().contains(bullet));
    }

    @Test
    public void testObstacleBulletCollisionRemovesBullet() {
        BulletGamePlugin bulletGamePlugin = new BulletGamePlugin();
        Bullet bullet = bulletGamePlugin.createBullet(0,0,0,1,1);
        TransformCP transformCP = (TransformCP) bullet.getComponent(EntityComponentTypes.TRANSFORM);
        transformCP.setCoord(new Coordinate(0, 0));

        Obstacle obstacle = new Obstacle();
        obstacle.addComponent(new TransformCP(new Coordinate(0, 0), 0, 1));

        assertTrue(world.getEntities(Obstacle.class).isEmpty());
        assertTrue(world.getEntities(Bullet.class).isEmpty());

        world.addEntity(obstacle);
        world.addEntity(bullet);

        assertFalse(world.getEntities(Obstacle.class).isEmpty());
        assertFalse(world.getEntities(Bullet.class).isEmpty());

        collisionDetector.process(gameData, world);

        assertFalse(world.getEntities(Obstacle.class).isEmpty());
        assertTrue(world.getEntities(Bullet.class).isEmpty());
    }


    @Test
    public void testCollisionDecreasesHealth(){
        BulletGamePlugin bulletGamePlugin = new BulletGamePlugin();
        Bullet bullet = bulletGamePlugin.createBullet(0,0,0,1,1);

        Entity entity = new Entity();
        entity.addComponent(new HealthCP());
        HealthCP healthCP= (HealthCP) entity.getComponent(EntityComponentTypes.HEALTH);
        healthCP.setHealth(10);
        entity.addComponent(new PlayerCollisionHandler());
        entity.getComponent(EntityComponentTypes.COLLISION).setEntity(entity);
        assertEquals(10, healthCP.getHealth());

        //bullet's coordinates are changed in create bullet. The coordinates below reflect that change
        entity.addComponent(new TransformCP(new Coordinate(2.5,0),0,1));

        healthCP = (HealthCP) entity.getComponent(EntityComponentTypes.HEALTH);

        world.addEntity(bullet);
        world.addEntity(entity);
        collisionDetector.process(gameData, world);

        assertEquals(9, healthCP.getHealth());

    }
/*
    @Test
    public void testKillsIncreaseScore() {
        Bullet bullet = new Bullet();
        bullet.addComponent(new CollisionCP(bullet));
        bullet.addComponent(new TransformCP(
                new Coordinate(0,0),
                0,
                1
        ));
        bullet.setEntityType(EEntityTypes.BULLET);

        Entity entity = new Entity();
        entity.addComponent(new CollisionCP(entity));
        entity.addComponent(new TransformCP(
                new Coordinate(0,0),
                0,
                1
        ));
        entity.setEntityType(EEntityTypes.ENEMY);

        world.addEntity(bullet);
        world.addEntity(entity);

        assertEquals(0, gameData.getScore());

        collisionDetector.process(gameData, world);
        entity.getComponent(EntityComponentTypes.COLLISION).process(gameData, world);

        assertEquals(1, gameData.getScore());
    }

    @Test
    public void testCollisionDoesNotIncreasesScore() {
        Entity player = new Entity();
        player.addComponent(new TransformCP(
                new Coordinate(0,0),
                0,
                1
        ));

        player.addComponent(new CollisionCP(player));
        player.setEntityType(EEntityTypes.PLAYER);

        Entity enemy = new Entity();
        enemy.addComponent(new CollisionCP(enemy));
        enemy.addComponent(new TransformCP(
                new Coordinate(0,0),
                0,
                1
        ));
        enemy.setEntityType(EEntityTypes.ENEMY);

        world.addEntity(player);
        world.addEntity(enemy);

        assertEquals(0, gameData.getScore());

        collisionDetector.process(gameData, world);
        player.getComponent(EntityComponentTypes.COLLISION).process(gameData, world);

        assertEquals(0, gameData.getScore());
    } */
}