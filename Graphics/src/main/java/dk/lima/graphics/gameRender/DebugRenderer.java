package dk.lima.graphics.gameRender;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.EGameInputs;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.player.Player;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DebugRenderer implements IGraphicsService {
    private Canvas debugCanvas;
    private GraphicsContext gc;
    private boolean pressed = false;
    private boolean shouldShow = false;

    @Override
    public Node createComponent(GameData gameData, World world) {
        debugCanvas = new Canvas(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gc = debugCanvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);
        gc.setFill(Color.RED);

        return debugCanvas;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        gc.clearRect(0,0, gameData.getDisplayWidth(), gameData.getDisplayHeight() );

        if (gameData.getInputs().isPressed(EGameInputs.DEBUG) & !pressed) {
            pressed = true;
            shouldShow ^= true;
        } else if (!gameData.getInputs().isPressed(EGameInputs.DEBUG) & pressed) {
            pressed = false;
        }

        if (!shouldShow) {
            for(Entity entity: world.getEntities()) {
                TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);
                Coordinate entityCoord = transformCP.getCoord();
                Coordinate playerCoord = world.getPlayerPosition();
                double circleDiameter = 2 * transformCP.getSize();

                if(entity instanceof Player) {
                    gc.fillOval(gameData.getDisplayWidth() /2d - circleDiameter / 2d, gameData.getDisplayHeight() /2d - circleDiameter / 2d, circleDiameter, circleDiameter);
                } else {
                    gc.fillOval(entityCoord.getX() - playerCoord.getX() + gameData.getDisplayWidth() / 2d - circleDiameter / 2d, entityCoord.getY() - playerCoord.getY() + gameData.getDisplayHeight() / 2d - circleDiameter / 2d, circleDiameter, circleDiameter);
                }
            }
        }
    }

    @Override
    public void showComponent(Boolean shouldShow) {
        debugCanvas.setVisible(shouldShow);
    }
}

