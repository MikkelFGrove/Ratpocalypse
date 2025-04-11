package dk.lima.common.entitycomponents;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;

public class DamageCP implements IEntityComponent {
    private double attackDamage;

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.DAMAGE;
    }

    @Override
    public void setEntity(Entity entity) {
    }

    @Override
    public void process(GameData gameData, World world) {
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }
}
