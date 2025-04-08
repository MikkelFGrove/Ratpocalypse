import dk.lima.pathfindingComponent.PathfindingComponent;
import dk.lima.common.entity.IEntityComponent;

module PathfindingComponent {
    requires Common;
    requires CommonEntityCP;
    provides IEntityComponent with PathfindingComponent;
}