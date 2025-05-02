package dk.lima.graphics.gameRender;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.EGameInputs;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.player.Player;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import dk.lima.pathfindingComponent.PathfindingComponent;

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

        if (shouldShow) {
            for(Entity entity: world.getEntities()) {
                TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);
                Coordinate playerCoord = world.getPlayerPosition();
                Coordinate entityCoord = transformCP.getCoord();

                drawHitbox(entity,gameData, world, transformCP);

                IEntityComponent pathfindingComponent = entity.getComponent(EntityComponentTypes.PATHFINDING);
                if (pathfindingComponent instanceof PathfindingComponent pfComp) {
                    Coordinate[] path = pfComp.getPath();

                    if (path != null && path.length > 1) {
                        if (path.length > pfComp.getStepsTaken()) {
                            Coordinate to = path[pfComp.getStepsTaken()];
                            drawLine(entityCoord, to, playerCoord, gameData);
                        }

                        for (int i = pfComp.getStepsTaken(); i < path.length - 1; i++) {
                            Coordinate from = path[i];
                            Coordinate to = path[i + 1];
                            drawLine(from, to, playerCoord, gameData);
                        }
                    }
                }
            }
        }
    }

    public void drawHitbox(Entity entity, GameData gameData, World world, TransformCP transformCP) {
        Coordinate entityCoord = transformCP.getCoord();
        Coordinate playerCoord = world.getPlayerPosition();
        double circleDiameter = 2 * transformCP.getSize();

        if(entity instanceof Player) {
            gc.fillOval(gameData.getDisplayWidth() /2d - circleDiameter / 2d, gameData.getDisplayHeight() /2d - circleDiameter / 2d, circleDiameter, circleDiameter);
        } else {
            gc.fillOval(entityCoord.getX() - playerCoord.getX() + gameData.getDisplayWidth() / 2d - circleDiameter / 2d, entityCoord.getY() - playerCoord.getY() + gameData.getDisplayHeight() / 2d - circleDiameter / 2d, circleDiameter, circleDiameter);
        }
    }

    public void drawLine(Coordinate from, Coordinate to, Coordinate playerCoord, GameData gameData) {
        double fromX = from.getX() - playerCoord.getX() + gameData.getDisplayWidth() / 2d;
        double fromY = from.getY() - playerCoord.getY() + gameData.getDisplayHeight() / 2d;
        double toX = to.getX() - playerCoord.getX() + gameData.getDisplayWidth() / 2d;
        double toY = to.getY() - playerCoord.getY() + gameData.getDisplayHeight() / 2d;

        gc.setStroke(Color.GREEN);
        gc.setLineWidth(5);
        gc.strokeLine(fromX, fromY, toX, toY);
    }

    @Override
    public void showComponent(Boolean shouldShow) {
        debugCanvas.setVisible(shouldShow);
    }
}

