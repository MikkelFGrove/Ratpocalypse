package dk.lima.persistence;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IPostEntityProcessingService;

import java.io.FileWriter;
import java.io.IOException;

public class PersistentScore implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        saveScoreToFile(gameData.getScore());
    }

    public void saveScoreToFile(int score) {
        try {
            FileWriter writer = new FileWriter("scores.txt", false);
            writer.write(String.valueOf(score));
            writer.close();

        } catch (IOException e) {
            System.out.println("FEJLFEJLFEJFLEJLE");
            e.printStackTrace();
        }
    }
}
