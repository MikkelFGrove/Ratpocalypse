package dk.lima.common.entitycomponents;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.weapon.IWeaponSPI;

import java.util.Random;

public class WeaponCP implements IEntityComponent {
    private Entity entity;
    private IWeaponSPI weaponSPI;
    private int attackChance;
    private boolean shouldAttack;

    public WeaponCP() {
    }

    public WeaponCP(Entity entity, IWeaponSPI weaponSPI, int attackChance, boolean shouldAttack) {
        this.entity = entity;
        this.weaponSPI = weaponSPI;
        this.attackChance = attackChance;
        this.shouldAttack = shouldAttack;
    }

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.WEAPON;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void process(GameData gameData, World world) {
        Random rand = new Random();

        if (shouldAttack & rand.nextInt(attackChance) == 0) {

            if (weaponSPI != null) {
                weaponSPI.shoot(entity, gameData, world);
            }
        }
    }

    public boolean shouldAttack() {
        return shouldAttack;
    }

    public void setShouldAttack(boolean shouldAttack) {
        this.shouldAttack = shouldAttack;
    }

    public int getAttackChance() {
        return attackChance;
    }

    public void setAttackChance(int attackChance) {
        this.attackChance = attackChance;
    }

    public IWeaponSPI getWeaponSPI() {
        return weaponSPI;
    }

    public void setWeaponSPI(IWeaponSPI weaponSPI) {
        this.weaponSPI = weaponSPI;
    }
}
