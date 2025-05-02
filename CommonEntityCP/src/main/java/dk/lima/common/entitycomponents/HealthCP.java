package dk.lima.common.entitycomponents;

import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;

public class HealthCP implements IEntityComponent {
    private Entity entity;
    private double maxHealth;
    private double health;

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.HEALTH;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void process(GameData gameData, World world) {
        if (isDead()) {
            if (entity.getEntityType() == EEntityTypes.ENEMY) {
                gameData.addScore(1);
            }
            world.removeEntity(entity);
        }
    }

    private boolean isDead() {
        return health <= 0;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void subtractHealth(double damage) {
        health -= damage;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }
}
