import dk.lima.common.data.IEntityComponent;
import dk.lima.pathfindingComponent.PathfindingComponent;

module PathfindingComponent {
    requires Common;
    requires CommonEntityCP;

    exports dk.lima.pathfindingComponent;
    provides IEntityComponent with PathfindingComponent;
}