package dk.lima.worldgenerator;

import dk.lima.collisionSystem.CollisionDetector;
import dk.lima.common.data.Coordinate;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.playersystem.PlayerCollisionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorldGeneratorTest {

    private static GameData gameData;
    private static World world;
    private static CollisionDetector collisionDetector;
    private static WorldGenerator worldGenerator;

    @BeforeAll
    public static void setup() {
        gameData = new GameData();
        world = new World();
        collisionDetector = new CollisionDetector();
        worldGenerator = new WorldGenerator();
    }

    @AfterEach
    public void removeEntities() {
        for (Entity e : world.getEntities()) {
            world.removeEntity(e);
        }
    }

    @Test
    public void testHazardsDecreaseHealth(){
        Entity entity = new Entity();
        entity.addComponent(new HealthCP());
        HealthCP healthCP= (HealthCP) entity.getComponent(EntityComponentTypes.HEALTH);
        healthCP.setHealth(10);
        entity.addComponent(new PlayerCollisionHandler());
        entity.getComponent(EntityComponentTypes.COLLISION).setEntity(entity);
        entity.addComponent(new TransformCP(new Coordinate(0,0),0,1));

        Entity hazard = worldGenerator.createHazard(gameData, new Coordinate(0,0));
        world.addEntity(hazard);
        world.addEntity(entity);

        assertEquals(10, healthCP.getHealth());

        collisionDetector.process(gameData, world);
        assertTrue(healthCP.getHealth() < 10);

    }
}