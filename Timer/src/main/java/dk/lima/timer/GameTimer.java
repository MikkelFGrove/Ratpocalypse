package dk.lima.timer;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IGamePluginService;

import java.util.concurrent.*;

public class GameTimer implements IGamePluginService {
    private ScheduledExecutorService executor;

    @Override
    public void start(GameData gameData, World world) {
        executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        });
        executor.scheduleAtFixedRate(new TimerTask(gameData), 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void stop(GameData gameData, World world) {
        executor.shutdown();
        executor = null;
    }
}
