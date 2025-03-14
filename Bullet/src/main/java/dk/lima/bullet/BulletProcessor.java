package dk.lima.bullet;

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
            bullet.setX(bullet.getX() + changeX * 3);
            bullet.setY(bullet.getY() + changeY * 3);
            if(bullet.getX() + world.getPlayerX() > gameData.getDisplayWidth() ||
            bullet.getY() + world.getPlayerY() > gameData.getDisplayHeight() ||
            bullet.getX() + world.getPlayerX() < 0 ||
            bullet.getY() + world.getPlayerY() < 0) {
                world.removeEntity(bullet);
            }
        }
    }
}
