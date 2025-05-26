package dk.lima.common.entitycomponents;

import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.weapon.IWeaponSPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class WeaponCPTest {

    private WeaponCP weaponCP;
    private World world;
    private GameData gameData;
    private Entity dummyEntity;

    @BeforeEach
    public void setup() {
        weaponCP = new WeaponCP();
        world = new World();
        gameData = new GameData();
        dummyEntity = new Entity();
        dummyEntity.setEntityType(EEntityTypes.PLAYER);
    }

    @Test
    public void shoot() {

        WeaponCP weaponCP = new WeaponCP(dummyEntity, new DummyWeaponSPI(), 1, true);
        dummyEntity.addComponent(weaponCP);
        weaponCP.process(gameData, world);
        assertFalse(world.getEntities(EEntityTypes.BULLET).isEmpty());
    }
}

class DummyWeaponSPI implements IWeaponSPI {
    @Override
    public void shoot(Entity shooter, GameData gameData, World world) {
        // Create a dummy bullet entity and add it to the world
        Entity bullet = new Entity();
        bullet.setEntityType(EEntityTypes.BULLET);
        world.addEntity(bullet);
    }
}
