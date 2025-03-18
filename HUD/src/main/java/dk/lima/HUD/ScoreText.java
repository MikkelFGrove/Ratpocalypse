package dk.lima.HUD;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IGraphicsComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreText implements IGraphicsComponent {
    private Text scoreText;

    @Override
    public Node createComponent(GameData gameData) {
        scoreText = new Text(10, 40, "Score: 0");
        scoreText.setFont(new Font("Arial", 20));
        scoreText.setFill(Color.WHITE);
        return scoreText;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        scoreText.setText(String.format("Score: %d", gameData.getScore()));
    }

    @Override
    public void showComponent(Boolean shouldShow) {
        scoreText.setVisible(shouldShow);
    }
}
