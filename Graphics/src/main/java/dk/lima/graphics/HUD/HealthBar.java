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
import javafx.scene.paint.Color;

public class HealthBar implements IGraphicsService {
    private Canvas healthCanvas;
    private GraphicsContext gc;
    private final Coordinate drawPosition = new Coordinate(8, 75);
    private final double healthBarHeight = 15;
    private final double healthBarWidth = 200;

    @Override
    public Node createComponent(GameData gameData, World world) {
        healthCanvas = new Canvas(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gc = healthCanvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        gc.setFill(Color.GREY);
        gc.fillRect(drawPosition.getX(), drawPosition.getY(), healthBarWidth, healthBarHeight);

        gc.setFill(Color.RED);
        gc.fillRect(drawPosition.getX(), drawPosition.getY(), healthBarWidth, healthBarHeight);

        return healthCanvas;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        gc.clearRect(drawPosition.getX(), drawPosition.getY(), healthBarWidth, healthBarHeight);

        gc.setFill(Color.GREY);
        gc.fillRect(drawPosition.getX(), drawPosition.getY(), healthBarWidth, healthBarHeight);

        for(Entity entity: world.getEntities()) {
            if (entity instanceof Player & entity.getComponent(EntityComponentTypes.HEALTH) != null) {
                HealthCP healthCP = (HealthCP) entity.getComponent(EntityComponentTypes.HEALTH);
                double healthRatio = healthCP.getHealth() / healthCP.getMaxHealth();

                gc.setFill(Color.RED);
                gc.fillRect(drawPosition.getX(), drawPosition.getY(), healthBarWidth * healthRatio, healthBarHeight);
            }
        }

    }

    @Override
    public void showComponent(Boolean shouldShow) {
        healthCanvas.setVisible(shouldShow);
    }
}
