package dk.lima.bullet;

import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entitycomponents.ShapeCP;
import dk.lima.common.entitycomponents.TransformCP;
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
        bullet.setEntityType(EEntityTypes.BULLET);
        double size = 1.5;

        double changeX = Math.cos(Math.toRadians(rotation));
        double changeY = Math.sin(Math.toRadians(rotation));
        double bulletX = x + changeX * (radius + size);
        double bulletY = y + changeY * (radius + size);

        bullet.addComponent(new TransformCP(
                new Coordinate(bulletX, bulletY),
                rotation,
                size
        ));

        bullet.addComponent(new ShapeCP(
                new double[]{size, size, -size, size, -size, -size, size, -size},
                new int[]{0,0,0}

        ));

        return bullet;
    }
}
