package dk.lima.weapon;
import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
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
            double rotation = e.getRotation();
            double radius = e.getRadius();
            getBulletSpi().stream().findFirst().ifPresent(bulletSpi -> {world.addEntity(bulletSpi.createBullet(e.getX(),e.getY(),rotation,radius));});
            lastShot = System.currentTimeMillis();
        }



    }
}
