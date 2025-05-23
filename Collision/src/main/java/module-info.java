import dk.lima.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonBullet;
    requires CommonObstacle;
    requires CommonEntityCP;

    provides IPostEntityProcessingService with dk.lima.collisionSystem.CollisionDetector;
}