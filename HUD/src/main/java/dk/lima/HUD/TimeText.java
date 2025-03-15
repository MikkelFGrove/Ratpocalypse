package dk.lima.HUD;

import dk.lima.common.data.GameData;
import dk.lima.common.graphics.IGraphicsComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TimeText implements IGraphicsComponent {
    private static Text timeText;

    @Override
    public Node createComponent(GameData gameData) {
        timeText = new Text(10, 20, "Time: 00:00");
        timeText.setFont(new Font("Arial", 20));
        timeText.setFill(Color.WHITE);
        return timeText;
    }

    @Override
    public void updateComponent(GameData gameData) {
        timeText.setText(String.format("Time: %02d:%02d", gameData.getDuration().toMinutes() % 60, gameData.getDuration().toSeconds() % 60));
    }

    @Override
    public void showComponent(Boolean shouldShow) {
        timeText.setVisible(shouldShow);
    }
}
