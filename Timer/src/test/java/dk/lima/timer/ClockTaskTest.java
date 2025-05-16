package dk.lima.timer;

import dk.lima.common.data.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class ClockTaskTest {
    private GameData gameData;
    private ClockTask clockTask;

    @BeforeEach
    public void setup() {
        gameData = new GameData();
        clockTask = new ClockTask();
        clockTask.setGameData(gameData);
    }

    @Test
    public void testClockTask() {
        gameData.setGameRunning(true);
        Duration before = gameData.getDuration();
        clockTask.run();
        Duration after = gameData.getDuration();


        assertEquals(before.plusSeconds(1), after);

        gameData.setGameRunning(false);
        clockTask.run();
        assertEquals(after, gameData.getDuration());
    }
}