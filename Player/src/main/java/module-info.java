import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.playersystem.PlayerControlSystem;
import dk.lima.playersystem.PlayerPlugin;

module Player {
    requires Common;
    requires CommonPlayer;
    provides IGamePluginService with PlayerPlugin;
    provides IEntityProcessingService with PlayerControlSystem;
}