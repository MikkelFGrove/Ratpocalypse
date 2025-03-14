import dk.lima.common.services.IGamePluginService;

module Timer {
    requires Common;

    provides IGamePluginService with dk.lima.timer.GameTimer;
}