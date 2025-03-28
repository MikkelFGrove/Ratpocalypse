package dk.lima.bullet;

import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.bullet.Bullet;

public class BulletProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            TransformCP transformCP = (TransformCP) bullet.getComponent(EntityComponentTypes.TRANSFORM);
            Coordinate coord = transformCP.getCoord();

            double changeX = Math.cos(Math.toRadians(transformCP.getRotation()));
            double changeY = Math.sin(Math.toRadians(transformCP.getRotation()));

            transformCP.setCoord(new Coordinate(coord.getX() + changeX * 3, coord.getY() + changeY * 3));

            if((coord.getX() + gameData.getDisplayWidth() / 2d - world.getPlayerPosition().getX()) > gameData.getDisplayWidth() ||
                    (coord.getY() + gameData.getDisplayHeight() / 2d - world.getPlayerPosition().getY()) > gameData.getDisplayHeight() ||
                    (coord.getX() + gameData.getDisplayWidth() / 2d - world.getPlayerPosition().getX()) < 0 ||
                    (coord.getY() + gameData.getDisplayHeight() / 2d - world.getPlayerPosition().getY()) < 0) {
                world.removeEntity(bullet);
            }
        }
    }
}
