package dk.lima.meleerat;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MeleeRatTest {
    private GameData gameData;
    private World world;
    private MeleeRatPlugin meleeRatPlugin;

    @BeforeEach
    void setUp() {
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
    public void testRatPluginSpawnsRat() {
        assertTrue(world.getEntities(MeleeRat.class).isEmpty());
        world.addEntity(meleeRatPlugin.createEnemy(gameData, new Coordinate(0,0)));
        assertFalse(world.getEntities(MeleeRat.class).isEmpty());
    }

}