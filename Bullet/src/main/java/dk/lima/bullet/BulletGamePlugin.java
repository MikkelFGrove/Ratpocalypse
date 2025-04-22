package dk.lima.bullet;

import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.entitycomponents.ShapeCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.bullet.Bullet;
import dk.lima.common.bullet.IBulletSPI;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

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

        bullet.addComponent(new BulletCollisionHandler());
        bullet.getComponent(EntityComponentTypes.COLLISION).setEntity(bullet);

        for (IEntityComponent component : getEntityComponents()) {
            switch (component.getType()) {
                case TRANSFORM -> {
                    bullet.addComponent(new TransformCP(
                            new Coordinate(bulletX, bulletY),
                            rotation,
                            size
                    ));
                }
                case SHAPE -> {
                    bullet.addComponent(new ShapeCP(
                            new double[]{size, size, -size, size, -size, -size, size, -size},
                            new int[]{0,0,0}

                    ));
                }
            }
        }
        return bullet;
    }
    public static Collection<? extends IEntityComponent> getEntityComponents() {
        return ServiceLoader.load(IEntityComponent.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}
