package dk.lima.graphics.HUD;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.player.Player;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

public class HealthBar implements IGraphicsService {
    private Canvas healthCanvas;
    private GraphicsContext gc;
    private final Coordinate drawPosition = new Coordinate(8, 50);
    private final double healthBarHeight = 15;
    private final double healthBarWidth = 200;
    private Text healthText;

    @Override
    public Node createComponent(GameData gameData, World world) {
        Pane healthPane = new Pane();
        healthCanvas = new Canvas(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gc = healthCanvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        gc.setFill(Color.GREY);
        gc.fillRect(drawPosition.getX() + 26, drawPosition.getY() + 3, healthBarWidth, healthBarHeight);

        gc.setFill(Color.RED);
        gc.fillRect(drawPosition.getX() + 26, drawPosition.getY() + 3, healthBarWidth, healthBarHeight);

        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("heart_24_v2.png")),
                20, 20, false, false);

        gc.drawImage(image, drawPosition.getX(), drawPosition.getY());

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeRect(drawPosition.getX() + 26, drawPosition.getY() + 3, healthBarWidth, healthBarHeight);

        healthText = new Text(drawPosition.getX() + 100, drawPosition.getY() + 15, "0/0");
        healthText.setFont(new Font("Arial", 14));
        healthText.setFill(Color.WHITE);

        healthPane.getChildren().add(healthCanvas);
        healthPane.getChildren().add(healthText);

        return healthPane;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        gc.clearRect(drawPosition.getX() + 26, drawPosition.getY() + 3, healthBarWidth, healthBarHeight);

        gc.setFill(Color.GREY);
        gc.fillRect(drawPosition.getX() + 26, drawPosition.getY() + 3, healthBarWidth, healthBarHeight);

        for(Entity entity: world.getEntities()) {
            if (entity instanceof Player & entity.getComponent(EntityComponentTypes.HEALTH) != null) {
                HealthCP healthCP = (HealthCP) entity.getComponent(EntityComponentTypes.HEALTH);
                double healthRatio = healthCP.getHealth() / healthCP.getMaxHealth();

                gc.setFill(Color.RED);
                gc.fillRect(drawPosition.getX() + 26, drawPosition.getY() + 3, healthBarWidth * healthRatio, healthBarHeight);

                String healthString = String.format("%.0f/%.0f",healthCP.getHealth(),healthCP.getMaxHealth());
                healthText.setText(healthString);
            }
        }

    }

    @Override
    public void showComponent(Boolean shouldShow) {
        healthCanvas.setVisible(shouldShow);
    }
}
