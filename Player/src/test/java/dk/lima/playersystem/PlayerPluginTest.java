package dk.lima.playersystem;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerPluginTest {
    private GameData gameData;
    private World world;
    private PlayerPlugin playerPlugin;

    @BeforeEach
    public void setup() {
        gameData = new GameData();
        world = new World();
        playerPlugin = new PlayerPlugin();
    }

    @Test
    public void testPlayerPluginSpawnsPlayer() {
        assertTrue(world.getEntities().isEmpty());

        playerPlugin.start(gameData, world);

        assertFalse(world.getEntities(Player.class).isEmpty());
    }
}