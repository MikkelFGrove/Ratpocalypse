package dk.lima.companion;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.companion.Companion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanionTest {
    private static World world;
    private static GameData gameData;

    @BeforeAll
    static void setup() {
        gameData = new GameData();
        world = new World();
    }

    @Test
    void testSpawn() {
        CompanionPlugin plugin = new CompanionPlugin();
        plugin.start(gameData, world);
        boolean found = world.getEntities().stream().anyMatch(e -> e instanceof Companion);
        assertTrue(found);
    }
}