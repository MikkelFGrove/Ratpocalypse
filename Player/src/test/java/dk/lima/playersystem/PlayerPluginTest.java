package dk.lima.playersystem;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.player.Player;
import dk.lima.common.services.IEntityProcessingService;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    public void removeEntities() {
        for (Entity e : world.getEntities()) {
            world.removeEntity(e);
        }
    }

    @Test
    public void testPlayerPluginSpawnsPlayer() {
        assertTrue(world.getEntities(Player.class).isEmpty());

        playerPlugin.start(gameData, world);

        assertFalse(world.getEntities(Player.class).isEmpty());
    }

    @Test
    public void testPlayerDiesOnZeroHealth(){
        playerPlugin.start(gameData, world);
        assertFalse(world.getEntities(Player.class).isEmpty()); //is the player in the world
        Player player = (Player) world.getEntities(Player.class).get(0);
        HealthCP healthCP = (HealthCP) player.getComponent(EntityComponentTypes.HEALTH);
        double firstHealth = healthCP.getHealth();
        assertEquals(100.0, firstHealth);
        healthCP.setHealth(0);
        healthCP.process(gameData, world);
        assertTrue(world.getEntities(Player.class).isEmpty()); //is the player removed from the world?
    }


}