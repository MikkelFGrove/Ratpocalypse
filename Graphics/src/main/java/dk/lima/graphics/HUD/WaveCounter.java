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


public class WaveCounter implements IGraphicsService {
    private Text waveText;

    @Override
    public Node createComponent(GameData gameData, World world) {
        waveText = new Text(6, 66, "Wave: 0");
        waveText.setFont(new Font("Arial", 18));
        waveText.setFill(Color.BLACK);
        return waveText;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        waveText.setText(String.format("Wave: %d", gameData.getCurrentWave()));
    }

    @Override
    public void showComponent(Boolean shouldShow) {
        waveText.setVisible(shouldShow);
    }
}


