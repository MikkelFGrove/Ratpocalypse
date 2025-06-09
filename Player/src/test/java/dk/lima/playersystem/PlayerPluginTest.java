package dk.lima.playersystem;

import dk.lima.common.bullet.Bullet;
import dk.lima.common.data.*;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entitycomponents.WeaponCP;
import dk.lima.common.player.Player;
import dk.lima.common.weapon.IWeaponSPI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerPluginTest {
    private GameData gameData;
    private World world;
    private PlayerPlugin playerPlugin;
    private GameInputs gameInputs;

    @BeforeEach
    public void setup() {
        gameData = new GameData();
        world = new World();
        playerPlugin = new PlayerPlugin();
        gameInputs = new GameInputs();
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

    @Test
    public void testPlayerCanShoot() {
        playerPlugin.start(gameData, world);

        Player player = (Player) world.getEntities(Player.class).get(0);
        WeaponCP weaponCP = (WeaponCP) player.getComponent(EntityComponentTypes.WEAPON);
        weaponCP.setShouldAttack(true);
        weaponCP.setAttackChance(1);
        weaponCP.setWeaponSPI(new DummyWeaponSPI());
        weaponCP.process(gameData, world);
        assertFalse(world.getEntities(EEntityTypes.BULLET).isEmpty());
    }


    // So I don't need to rely on the ServiceLoader

    class DummyWeaponSPI implements IWeaponSPI {
        @Override
        public void shoot(Entity shooter, GameData gameData, World world) {
            // Create a dummy bullet entity and add it to the world
            Entity bullet = new Entity();
            bullet.setEntityType(EEntityTypes.BULLET);
            world.addEntity(bullet);
        }
    }


}