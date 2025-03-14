package dk.lima.bullet;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.bullet.Bullet;

public class BulletProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));

            bullet.setPosition(new Coordinate(bullet.getPosition().getX() + changeX * 3, bullet.getPosition().getY() + changeY * 3));
            if(bullet.getPosition().getX() + world.getPlayerPosition().getX() > gameData.getDisplayWidth() ||
            bullet.getPosition().getY() + world.getPlayerPosition().getY() > gameData.getDisplayHeight() ||
            bullet.getPosition().getX() + world.getPlayerPosition().getX() < 0 ||
            bullet.getPosition().getY() + world.getPlayerPosition().getY() < 0) {
                world.removeEntity(bullet);
            }
        }
    }
}
