package dk.lima.persistence;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;


import static org.junit.jupiter.api.Assertions.*;
class PersistentScoreTest {

    private final static String TEST_SCORE_FILE = "scores.txt";

    @BeforeEach
    void setUp() {
        new File(TEST_SCORE_FILE).delete();
    }

    @AfterEach
    void tearDown() {
        new File(TEST_SCORE_FILE).delete();
    }

    @Test
    void testScore() {
        // First score saved
        GameData gameDataFirstScore = new GameData();
        gameDataFirstScore.setScore(34);

        PersistentScore firstPersistentScore = new PersistentScore();
        firstPersistentScore.process(gameDataFirstScore, new World());

        // Second score saved
        GameData gameDataSecondScore = new GameData();
        gameDataSecondScore.setScore(24);

        PersistentScore secondPersistentScore = new PersistentScore();
        secondPersistentScore.process(gameDataSecondScore, new World());

        // The highscore should still be 34
        assertEquals(34, gameDataSecondScore.getHighscore());
    }
}