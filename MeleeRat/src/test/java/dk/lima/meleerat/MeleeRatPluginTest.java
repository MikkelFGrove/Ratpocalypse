package dk.lima.meleerat;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.HealthCP;


import static org.junit.jupiter.api.Assertions.*;

class MeleeRatPluginTest {
    private GameData gameData;
    private World world;
    private MeleeRatPlugin meleeRatPlugin;

    @BeforeEach
    public void setup() {
        gameData = new GameData();
        world = new World();
        meleeRatPlugin = new MeleeRatPlugin();
    }

    @AfterEach
    public void removeEntities() {
        for (Entity e : world.getEntities()) {
            world.removeEntity(e);
        }
    }

    @Test
    void testMeleeRatDiesOnZeroHealth() {
        gameData.setGameRunning(true);
        Entity meleeRat = meleeRatPlugin.createEnemy(gameData, new Coordinate(100, 100));
        world.addEntity(meleeRat);

        assertTrue(world.getEntities().contains(meleeRat)); // confirms MeleeRat is in the world
        HealthCP health = (HealthCP) meleeRat.getComponent(EntityComponentTypes.HEALTH); // gets the MeleeRat's sat health
        assertEquals(50, health.getHealth());
        
        health.setHealth(0);
        health.process(gameData, world);
        assertFalse(world.getEntities().contains(meleeRat));
    }

    @Test
    public void testEnemiesKilledIsTracked() {
        Entity meleeRat = meleeRatPlugin.createEnemy(gameData, new Coordinate(100,100));
        world.addEntity(meleeRat);
        assertTrue(world.getEntities().contains(meleeRat));

        int startScore = gameData.getScore(); // initial score to 0

        HealthCP health = (HealthCP) meleeRat.getComponent(EntityComponentTypes.HEALTH);
        health.setHealth(0);
        health.process(gameData, world);
        assertEquals(startScore + 1, gameData.getScore()); // makes sure the score has been incremented
    }
}