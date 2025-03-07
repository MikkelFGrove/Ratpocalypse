import dk.lima.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;   
    provides IPostEntityProcessingService with dk.lima.collisionSystem.CollisionDetector;
}