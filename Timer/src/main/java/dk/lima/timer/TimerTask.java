package dk.lima.timer;

import dk.lima.common.data.GameData;

import java.time.Duration;

public class TimerTask implements Runnable{
    private GameData gameData;

    public TimerTask(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void run() {
        if (gameData.isGameRunning()) {
            gameData.addDuration(Duration.ofSeconds(1));
        }
    }
}
