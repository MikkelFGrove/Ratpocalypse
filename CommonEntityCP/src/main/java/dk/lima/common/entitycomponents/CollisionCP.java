package dk.lima.common.entitycomponents;

import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;

public class CollisionCP implements IEntityComponent {
    Entity entity;
    EEntityTypes targetType;

    public CollisionCP(Entity entity, EEntityTypes targetType){
        this.entity = entity;
        this.targetType = targetType;
    }

    @Override
    public EntityComponentTypes getType() {
        return null;
    }

    @Override
    public void setEntity(Entity entity) {

    }

    @Override
    public void process(GameData gameData, World world) {

    }
}
