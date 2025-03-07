package dk.lima.bullet;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.commonBullet.Bullet;

public class BulletProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));

            bullet.setX(bullet.getX() + changeX * 3);
            bullet.setY(bullet.getY() + changeY * 3);
        }
    }
}
