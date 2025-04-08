package dk.lima.weapon;
import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.weapon.IWeaponSPI;
import dk.lima.common.bullet.IBulletSPI;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class Rifle implements IWeaponSPI{
    final double fireRate = 100;
    private long lastShot = 0;

    private Collection<? extends IBulletSPI> getBulletSpi() {
        return ServiceLoader.load(IBulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }


    @Override
    public void shoot(Entity e, GameData gameData, World world) {
        if(System.currentTimeMillis() - lastShot > fireRate) {
            TransformCP transformCP = (TransformCP) e.getComponent(EntityComponentTypes.TRANSFORM);

            double rotation = transformCP.getRotation();
            double radius = transformCP.getSize();
            Coordinate coord = transformCP.getCoord();
            getBulletSpi().stream().findFirst().ifPresent(bulletSpi -> {world.addEntity(bulletSpi.createBullet(coord.getX(),coord.getY(),rotation,radius));});
            lastShot = System.currentTimeMillis();
        }
    }
}
