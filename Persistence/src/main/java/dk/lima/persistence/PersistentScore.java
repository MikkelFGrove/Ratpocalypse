package dk.lima.persistence;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IPostEntityProcessingService;

import java.io.*;

public class PersistentScore implements IPostEntityProcessingService {

    private int highScore;
    private static final String SCORE_FILE = "scores.txt";

    @Override
    public void process(GameData gameData, World world) {
        highScore = readScoreFromFile();
        if (gameData.getScore() > highScore) {
            highScore = gameData.getScore();
            saveScoreToFile(gameData.getScore());
        }
    }

    public void saveScoreToFile(int score) {
        try (FileWriter writer = new FileWriter(SCORE_FILE, false)) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            System.out.println("FEJLFEJLFEJFLEJLE");
            e.printStackTrace();
        }
    }

    public int readScoreFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line = reader.readLine();

            if (line != null) {
                highScore = Integer.parseInt(line);
            }

        } catch (IOException e) {
            highScore = 0;
        }
        return highScore;
    }
}
