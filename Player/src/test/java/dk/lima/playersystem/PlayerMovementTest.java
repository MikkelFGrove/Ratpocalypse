package dk.lima.playersystem;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.EGameInputs;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.MovementCP;
import dk.lima.common.player.Player;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerMovementTest {
    private static World world;
    private static GameData gameData;
    private static PlayerPlugin playerPlugin;

    @BeforeAll
    public static void setup() {
        world = new World();
        gameData = new GameData();
        playerPlugin = new PlayerPlugin();
    }

    @BeforeEach
    public void init() {
        world = new World();
        gameData = new GameData();

        assertTrue(world.getEntities().isEmpty());
        playerPlugin.start(gameData, world);
        assertFalse(world.getEntities().isEmpty());
    }

    @AfterEach
    public void tearDown() {
        playerPlugin.stop(gameData, world);
        world = null;
        gameData = null;
    }

    @Test
    public void playerMovesRightWithInput() {
        Coordinate spawnPosition = world.getPlayerPosition().clone();

        gameData.getInputs().setInput(EGameInputs.RIGHT, true);

        Entity player = world.getEntities(Player.class).getFirst();
        MovementCP movementCP = (MovementCP) player.getComponent(EntityComponentTypes.MOVEMENT);
        movementCP.process(gameData, world);

        assertNotEquals(spawnPosition, world.getPlayerPosition());
    }

    @Test
    public void playerMovesLeftWithInput() {
        Coordinate spawnPosition = world.getPlayerPosition().clone();

        gameData.getInputs().setInput(EGameInputs.LEFT, true);

        Entity player = world.getEntities(Player.class).getFirst();
        MovementCP movementCP = (MovementCP) player.getComponent(EntityComponentTypes.MOVEMENT);
        movementCP.process(gameData, world);

        assertNotEquals(spawnPosition, world.getPlayerPosition());
    }

    @Test
    public void playerMovesUpWithInput() {
        Coordinate spawnPosition = world.getPlayerPosition().clone();

        gameData.getInputs().setInput(EGameInputs.UP, true);

        Entity player = world.getEntities(Player.class).getFirst();
        MovementCP movementCP = (MovementCP) player.getComponent(EntityComponentTypes.MOVEMENT);
        movementCP.process(gameData, world);

        assertNotEquals(spawnPosition, world.getPlayerPosition());
    }

    @Test
    public void playerMovesDownWithInput() {
        Coordinate spawnPosition = world.getPlayerPosition().clone();

        gameData.getInputs().setInput(EGameInputs.DOWN, true);

        Entity player = world.getEntities(Player.class).getFirst();
        MovementCP movementCP = (MovementCP) player.getComponent(EntityComponentTypes.MOVEMENT);
        movementCP.process(gameData, world);

        assertNotEquals(spawnPosition, world.getPlayerPosition());
    }
}
