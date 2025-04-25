package dk.lima.enemy.rangedrat;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.DamageCP;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entitycomponents.ICollisionHandler;

public class RangedRatCollisionHandler implements ICollisionHandler {
    Entity entity;

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.COLLISION;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void process(GameData gameData, World world) {

    }

    @Override
    public void onCollide(Entity other, World world) {
        if ((entity.getComponent(EntityComponentTypes.HEALTH) != null) & (other.getComponent(EntityComponentTypes.DAMAGE) != null)) {
            HealthCP healthCP = (HealthCP) entity.getComponent(EntityComponentTypes.HEALTH);
            DamageCP damageCP2 = (DamageCP) other.getComponent(EntityComponentTypes.DAMAGE);
            healthCP.subtractHealth(damageCP2.getAttackDamage());
        }
    }
}
