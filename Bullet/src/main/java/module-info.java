import dk.lima.bullet.BulletGamePlugin;
import dk.lima.bullet.BulletProcessor;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.bullet.IBulletSPI;

module Bullet {
    requires CommonBullet;
    requires CommonEntityCP;
    requires Common;

    provides IGamePluginService with BulletGamePlugin;
    provides IEntityProcessingService with BulletProcessor;
    provides IBulletSPI with BulletGamePlugin;
}