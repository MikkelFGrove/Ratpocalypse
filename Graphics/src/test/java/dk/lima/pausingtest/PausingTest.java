package dk.lima.pausingtest;

import dk.lima.common.data.EGameInputs;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.graphics.menuRender.PauseMenu;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class PausingTest {
    private GameData gameData;
    private World world;
    private PauseMenu pauseMenu;
    private static final AtomicBoolean javafxInitialized = new AtomicBoolean(false);

    static {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown); // start JavaFX
            latch.await();
            javafxInitialized.set(true);
        } catch (IllegalStateException ignored) {
            javafxInitialized.set(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() throws Exception {
        gameData = new GameData();
        world = new World();
        gameData.setGameRunning(true);

        CountDownLatch createLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            pauseMenu = new PauseMenu();
            pauseMenu.createComponent(gameData, world);
            createLatch.countDown();
        });
        createLatch.await();
    }

    @Test
    public void testPlayerPluginSpawnsPlayer() throws Exception {
        assertTrue(gameData.isGameRunning());

        gameData.getInputs().setInput(EGameInputs.PAUSE, true);

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            pauseMenu.updateComponent(gameData, world);
            latch.countDown();
        });
        latch.await();

        assertFalse(gameData.isGameRunning());
    }
}