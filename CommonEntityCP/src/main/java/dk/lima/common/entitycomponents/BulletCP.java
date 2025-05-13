package dk.lima.common.entitycomponents;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.bullet.Bullet;

public class BulletCP implements IEntityComponent {
    private Entity entity;

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.BULLET;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void process(GameData gameData, World world) {
        TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);
        Coordinate coord = transformCP.getCoord();

        double changeX = Math.cos(Math.toRadians(transformCP.getRotation()));
        double changeY = Math.sin(Math.toRadians(transformCP.getRotation()));
        double velocity = 3.75;
        transformCP.setCoord(new Coordinate(coord.getX() + changeX * velocity, coord.getY() + changeY * velocity));

        if((coord.getX() + gameData.getDisplayWidth() / 2d - world.getPlayerPosition().getX()) > gameData.getDisplayWidth() |
                (coord.getY() + gameData.getDisplayHeight() / 2d - world.getPlayerPosition().getY()) > gameData.getDisplayHeight() |
                (coord.getX() + gameData.getDisplayWidth() / 2d - world.getPlayerPosition().getX()) < 0 |
                (coord.getY() + gameData.getDisplayHeight() / 2d - world.getPlayerPosition().getY()) < 0) {
            world.removeEntity(entity);
        }
    }
}
