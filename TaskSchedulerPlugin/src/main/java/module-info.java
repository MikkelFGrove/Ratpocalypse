import dk.lima.common.services.IGamePluginService;
import dk.lima.common.services.ITimeTask;

module TaskSchedulerPlugin {
    requires Common;

    provides IGamePluginService with dk.lima.taskScheduler.TaskSchedulerPlugin;

    uses ITimeTask;
}