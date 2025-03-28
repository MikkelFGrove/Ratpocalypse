package dk.lima.collisionSystem;

import dk.lima.common.bullet.Bullet;
import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entitycomponents.TransformCP;
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

    @Test
    public void testBulletEntityCollisionRemovesBoth() {
        Bullet bullet = new Bullet();
        bullet.addComponent(new TransformCP(
                new Coordinate(0,0),
                0,
                1
        ));

        Entity entity = new Entity();
        entity.addComponent(new TransformCP(
                new Coordinate(0,0),
                0,
                1
        ));

        world.addEntity(bullet);
        world.addEntity(entity);

        assertTrue(world.getEntities().contains(entity));
        assertTrue(world.getEntities().contains(bullet));

        collisionDetector.process(gameData, world);

        // Check that both the bullet and entity is removed
        assertFalse(world.getEntities().contains(entity));
        assertFalse(world.getEntities().contains(bullet));
    }
}