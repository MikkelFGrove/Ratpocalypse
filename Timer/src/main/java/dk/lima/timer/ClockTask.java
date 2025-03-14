package dk.lima.timer;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.ITimeTask;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ClockTask implements ITimeTask {
    private World world;
    private GameData gameData;

    @Override
    public void run() {
        if (gameData.isGameRunning()) {
            gameData.addDuration(Duration.ofSeconds(1));

            if (gameData.getDuration().toSeconds() % 10 == 0) {
                gameData.addScore(1);
            }
        }
    }

    @Override
    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public long getDelay() {
        return 0;
    }

    @Override
    public long getPeriod() {
        return 1;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return TimeUnit.SECONDS;
    }
}
