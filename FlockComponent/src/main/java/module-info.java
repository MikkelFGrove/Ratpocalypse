import dk.lima.common.entity.IEntityComponent;
import dk.lima.flockComponent.FlockCP;

module FlockComponent {
    requires Common;
    requires CommonEntityCP;
    requires CommonPathfinding;

    provides IEntityComponent with FlockCP;

    exports dk.lima.flockComponent;
}