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

public class TimeText implements IGraphicsService {
    private Pane timePane;
    private Text timeText;

    @Override
    public Node createComponent(GameData gameData, World world) {
        timePane = new Pane();
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("basic_clock.png")),
                    20, 20, false, false);
            ImageView imageView = new ImageView(image);
            imageView.setX(8);
            imageView.setY(5);
            timePane.getChildren().add(imageView);
        } catch (NullPointerException e) {
            System.out.println("Time image not found");
        }

        timeText = new Text(32, 22, "00:00");
        timeText.setFont(new Font("Arial", 18));
        timeText.setFill(Color.BLACK);
        timePane.getChildren().add(timeText);
        return timePane;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        timeText.setText(String.format("%02d:%02d", gameData.getDuration().toMinutes() % 60, gameData.getDuration().toSeconds() % 60));
    }

    @Override
    public void showComponent(Boolean shouldShow) {
        timePane.setVisible(shouldShow);
        for (Node children : timePane.getChildren()) {
            children.setVisible(shouldShow);
        }
    }
}
