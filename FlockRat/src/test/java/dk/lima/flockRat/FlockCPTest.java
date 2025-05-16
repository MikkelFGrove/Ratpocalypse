package dk.lima.flockRat;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.TransformCP;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlockCPTest {
    private World world;
    private GameData gameData;

    @BeforeEach
    public void setup() {
        world = new World();
        gameData = new GameData();
    }

    @AfterEach
    public void teardown() {
        world.getEntities().clear();
    }

    @Test
    public void flock() {
        for (int i = 0; i < 3; i++) {
            Entity e = new Entity();
            e.setEntityType(EEntityTypes.ENEMY);

            TransformCP transformCP = new TransformCP();
            transformCP.setCoord(new Coordinate(200 * i, 200 * i));
            transformCP.setSize(1);
            transformCP.setEntity(e);
            e.addComponent(transformCP);

            FlockCP flockCP = new FlockCP();
            flockCP.setEntity(e);
            e.addComponent(flockCP);

            world.addEntity(e);
        }

        for (Entity entity : world.getEntities()) {
            FlockCP flockCP = (FlockCP) entity.getComponent(EntityComponentTypes.FLOCK);
            assertFalse(flockCP.hasAttacked());

            flockCP.process(gameData, world);
            assertFalse(flockCP.hasAttacked());
        }
    }

    @Test
    public  void attackWithCritPop() {
        for (int i = 0; i < 2; i++) {
            Entity e = new Entity();
            e.setEntityType(EEntityTypes.ENEMY);

            TransformCP transformCP = new TransformCP();
            transformCP.setCoord(new Coordinate(0, 0));
            transformCP.setSize(1);
            transformCP.setEntity(e);
            e.addComponent(transformCP);

            FlockCP flockCP = new FlockCP();
            flockCP.setEntity(e);
            e.addComponent(flockCP);

            world.addEntity(e);
        }

        for (Entity entity : world.getEntities()) {
            FlockCP flockCP = (FlockCP) entity.getComponent(EntityComponentTypes.FLOCK);
            assertFalse(flockCP.hasAttacked());

            flockCP.process(gameData, world);
            assertTrue(flockCP.hasAttacked());
        }
    }

    @Test
    public  void attackWithEntitiesInFlock() {
        for (int i = 0; i < 3; i++) {
            Entity e = new Entity();
            e.setEntityType(EEntityTypes.ENEMY);

            TransformCP transformCP = new TransformCP();
            transformCP.setCoord(new Coordinate(0, 0));
            transformCP.setSize(1);
            transformCP.setEntity(e);
            e.addComponent(transformCP);

            FlockCP flockCP = new FlockCP();
            flockCP.setEntity(e);
            e.addComponent(flockCP);

            world.addEntity(e);
        }

        for (Entity entity : world.getEntities()) {
            FlockCP flockCP = (FlockCP) entity.getComponent(EntityComponentTypes.FLOCK);
            assertFalse(flockCP.hasAttacked());

            flockCP.process(gameData, world);
            assertTrue(flockCP.hasAttacked());
        }
    }
}