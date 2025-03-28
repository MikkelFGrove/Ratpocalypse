import dk.sdu.cbse.common.bullet.IBulletSPI;

module CommonEntityCP {
    requires Common;
    requires CommonBullet;
    requires CommonCollision;

    exports dk.sdu.cbse.common.entitycomponents;

    uses IBulletSPI;
}


