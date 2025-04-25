import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.services.IGamePluginService;
import dk.lima.worldgenerator.WorldGenerator;

module WorldGenerator {
    requires Common;
    requires CommonObstacle;
    requires CommonEntityCP;

    uses IEntityComponent;

    provides IGamePluginService with WorldGenerator;
}