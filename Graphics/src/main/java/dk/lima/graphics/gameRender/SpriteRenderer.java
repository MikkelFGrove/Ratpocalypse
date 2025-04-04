package dk.lima.graphics.gameRender;

import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entitycomponents.SpriteCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.graphics.IGraphicsService;

import dk.lima.common.player.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.Node;

public class SpriteRenderer implements IGraphicsService {

    private Canvas spriteCanvas;
    private GraphicsContext gc;



    @Override
    public Node createComponent(GameData gameData, World world) {
        spriteCanvas = new Canvas(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gc = spriteCanvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        for(Entity entity: world.getEntities()) {
            SpriteCP spriteCP = entity.getComponent(SpriteCP.class);
            TransformCP transformCP = entity.getComponent(TransformCP.class);
            if(spriteCP != null) {
                gc.drawImage(spriteCP.getImage(), transformCP.getCoord().getX()- transformCP.getSize(), transformCP.getCoord().getY()- transformCP.getSize(), gameData.tileSize, gameData.tileSize);
            }

        }

        return spriteCanvas;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        gc.clearRect(0,0, gameData.getDisplayWidth(), gameData.getDisplayHeight() );
        for(Entity entity: world.getEntities()) {
            SpriteCP spriteCP = entity.getComponent(SpriteCP.class);
            TransformCP transformCP = entity.getComponent(TransformCP.class);

            if (spriteCP != null) {
                if(entity instanceof Player) {
                    gc.drawImage(spriteCP.getImage(), gameData.getDisplayWidth() /2d - transformCP.getSize(), gameData.getDisplayHeight() / 2d - transformCP.getSize(), gameData.tileSize, gameData.tileSize);

                } else {
                    gc.drawImage(spriteCP.getImage(), transformCP.getCoord().getX() - transformCP.getSize() + gameData.getDisplayWidth() / 2d - world.getPlayerPosition().getX(), transformCP.getCoord().getY() - transformCP.getSize() +gameData.getDisplayHeight() / 2d - world.getPlayerPosition().getY(), gameData.tileSize, gameData.tileSize);
                }

            }
        }


    }

    @Override
    public void showComponent(Boolean shouldShow) {

    }
}