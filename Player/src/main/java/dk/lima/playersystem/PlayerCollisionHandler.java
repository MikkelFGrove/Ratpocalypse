package dk.lima.playersystem;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.ICollisionHandler;

public class PlayerCollisionHandler implements ICollisionHandler {
    Entity entity;
    @Override
    public void onCollide(Entity other, World world) {
        world.removeEntity(entity);
    }

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
}
