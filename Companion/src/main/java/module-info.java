import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.companion.CompanionPlugin;
import dk.lima.companion.CompanionProcessor;

module Companion {
    requires Common;
    requires CommonEntityCP;
    requires CommonPathfinding;

    provides IGamePluginService with CompanionPlugin;
    provides IEntityProcessingService with CompanionProcessor;

    uses dk.lima.common.entity.IEntityComponent;
}