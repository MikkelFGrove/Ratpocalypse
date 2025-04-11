package dk.lima.common.bullet;

public interface IBulletSPI {
    Bullet createBullet(double x, double y, double rotation, double radius, double attackDamage);
}
