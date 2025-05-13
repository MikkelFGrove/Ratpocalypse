package dk.lima.graphics.HUD;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IGraphicsService;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

public class ScoreText implements IGraphicsService {
    private Pane scorePane;
    private Text scoreText;

    @Override
    public Node createComponent(GameData gameData, World world) {
        scorePane = new Pane();
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dead_rat_20.png")),
                    20, 20, false, false);
            ImageView imageView = new ImageView(image);
            imageView.setX(8);
            imageView.setY(28);
            scorePane.getChildren().add(imageView);
        } catch (NullPointerException e) {
            System.out.println("Score image not found");
        }

        scoreText = new Text(32, 44, "0");
        scoreText.setFont(new Font("Arial", 18));
        scoreText.setFill(Color.BLACK);
        scorePane.getChildren().add(scoreText);
        return scorePane;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        scoreText.setText(String.format("%d", gameData.getScore()));
    }

    @Override
    public void showComponent(Boolean shouldShow) {
        scorePane.setVisible(shouldShow);
        for (Node children : scorePane.getChildren()) {
            children.setVisible(shouldShow);
        }
    }
}
