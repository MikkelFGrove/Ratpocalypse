package dk.lima.graphics.gameRender;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entitycomponents.SpriteCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.player.Player;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HealthRenderer implements IGraphicsService {
    private Canvas healthCanvas;
    private GraphicsContext gc;
    private final int healthSize = 20;

    @Override
    public Node createComponent(GameData gameData, World world) {
        healthCanvas = new Canvas(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gc = healthCanvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        return healthCanvas;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        gc.clearRect(0,0, gameData.getDisplayWidth(), gameData.getDisplayHeight() );

        for(Entity entity: world.getEntities()) {
            HealthCP healthCP = (HealthCP) entity.getComponent(EntityComponentTypes.HEALTH);
            TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);
            Coordinate entityCoord = transformCP.getCoord();
            Coordinate playerCoord = world.getPlayerPosition();

            if (healthCP != null) {
                if(entity instanceof Player) {
                    gc.setFill(Color.GRAY);
                    gc.fillRect(gameData.getDisplayWidth() /2d  - healthSize / 2d, gameData.getDisplayHeight() /2d  - 2 * transformCP.getSize(), healthSize, 5);

                    gc.setFill(Color.GREEN);
                    gc.fillRect(gameData.getDisplayWidth() /2d  - healthSize / 2d, gameData.getDisplayHeight() /2d  - 2 * transformCP.getSize(), healthSize - 5, 5);
                } else {
                    gc.setFill(Color.GRAY);
                    gc.fillRect(entityCoord.getX()- transformCP.getSize() / 2 - playerCoord.getX() + gameData.getDisplayWidth() / 2d - healthSize / 2d, entityCoord.getY() - 2 * transformCP.getSize() - playerCoord.getY() + gameData.getDisplayHeight() / 2d, healthSize, 5);

                    gc.setFill(Color.GREEN);
                    gc.fillRect(entityCoord.getX()- transformCP.getSize() / 2 - playerCoord.getX() + gameData.getDisplayWidth() / 2d  - healthSize / 2d, entityCoord.getY() - 2 * transformCP.getSize() - playerCoord.getY() + gameData.getDisplayHeight() / 2d, healthSize - 5, 5);
                }
            }
        }
    }

    @Override
    public void showComponent(Boolean shouldShow) {
        healthCanvas.setVisible(shouldShow);
    }
}
