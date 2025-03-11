package dk.lima.common.bullet;

public interface IBulletSPI {
    public Bullet createBullet(double x, double y, double rotation, double radius);
}
