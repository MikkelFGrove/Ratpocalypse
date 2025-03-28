package dk.lima.taskScheduler;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.services.ITimeTask;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;

public class TaskSchedulerPlugin implements IGamePluginService {
    private static ScheduledExecutorService executor;

    @Override
    public void start(GameData gameData, World world) {
        executor = Executors.newScheduledThreadPool(2, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        });
        scheduleTasks(gameData, world);
    }

    public void scheduleTasks(GameData gameData, World world) {
        for (ITimeTask timeTask : getITimeTasks()) {
            timeTask.setGameData(gameData);
            timeTask.setWorld(world);
            executor.scheduleAtFixedRate(timeTask, timeTask.getDelay(), timeTask.getPeriod(), timeTask.getTimeUnit());
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        executor.close();
    }

    public static Collection<? extends ITimeTask> getITimeTasks() {
        return ServiceLoader.load(ITimeTask.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
