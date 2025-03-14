import dk.lima.common.pathfinding.IPathfindingSPI;
import dk.lima.pathfinding.AStarPathfinding;

module Pathfinding {
    requires Common;
    requires CommonPathfinding;
    provides IPathfindingSPI with AStarPathfinding;
}