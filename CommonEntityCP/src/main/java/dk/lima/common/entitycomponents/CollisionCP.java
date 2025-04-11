package dk.lima.common.entitycomponents;

import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;

public class CollisionCP implements IEntityComponent {
    private Entity entity;
    private Entity target;
    private EEntityTypes targetType;
    private boolean collisionDetected = false;

    public CollisionCP(){}

    public CollisionCP(Entity entity){
        this.entity = entity;
    }

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.COLLISION;
    }

    @Override
    public void setEntity(Entity entity) {
    }

    @Override
    public void process(GameData gameData, World world) {
        if (world.getEntity(entity.getID()) != null && world.getEntity(target.getID()) != null) {
            if (collisionDetected) {
                if (!gameData.isTimeScoring() && ((entity.getEntityType() == EEntityTypes.BULLET && target.getEntityType() == EEntityTypes.ENEMY)
                        || (entity.getEntityType() == EEntityTypes.ENEMY && target.getEntityType() == EEntityTypes.BULLET))) {
                    gameData.setScore(gameData.getScore() + 1);
                }
                world.removeEntity(entity);
                world.removeEntity(target);
                clearCollision();
            }
        }
    }

    public void collison(Entity target){
        this.target = target;
        collisionDetected = true;
    }

    public void clearCollision(){
        target = null;
        collisionDetected = false;
    }

    public Entity getEntity() {
        return entity;
    }

    public EEntityTypes getTargetType() {
        return targetType;
    }

    public void setTargetType(EEntityTypes targetType) {
        this.targetType = targetType;
    }

    public boolean isCollisionDetected() {
        return collisionDetected;
    }

    public void setCollisionDetected(boolean collisionDetected) {
        this.collisionDetected = collisionDetected;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target){
        this.target = target;
    }
}
