package dk.lima.bullet;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.bullet.Bullet;
import dk.lima.common.bullet.IBulletSPI;

public class BulletGamePlugin implements IGamePluginService, IBulletSPI {
    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            world.removeEntity(bullet);
        }
    }

    @Override
    public Bullet createBullet(double x, double y, double rotation, double radius) {
        Bullet bullet = new Bullet();
        int size = 1;



        double changeX = Math.cos(Math.toRadians(rotation));
        double changeY = Math.sin(Math.toRadians(rotation));
        double bulletX = x + changeX * (radius + size);
        double bulletY = y + changeY * (radius + size);

        bullet.setRotation(rotation);
        bullet.setRadius(size);
        bullet.setX(bulletX);
        bullet.setY(bulletY);
        bullet.setPolygonCoordinates(size, size, -size, size, -size, -size, size, -size);
        return bullet;
    }
}
