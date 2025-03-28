import dk.lima.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonEntityCP;

    provides IPostEntityProcessingService with dk.lima.collisionSystem.CollisionDetector;
}