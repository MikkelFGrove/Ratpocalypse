import dk.lima.pathfindingComponent.PathfindingComponent;
import dk.lima.common.entity.IEntityComponent;

module PathfindingComponent {
    requires Common;
    requires CommonEntityCP;
    requires CommonPathfinding;
    provides IEntityComponent with PathfindingComponent;

    exports dk.lima.pathfindingComponent;
}