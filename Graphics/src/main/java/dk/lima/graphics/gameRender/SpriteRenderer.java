package dk.lima.graphics.gameRender;

import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.SpriteCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.graphics.IGraphicsService;

import dk.lima.common.player.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.TreeMap;

public class SpriteRenderer implements IGraphicsService {
    private Canvas spriteCanvas;
    private GraphicsContext gc;

    @Override
    public Node createComponent(GameData gameData, World world) {
        spriteCanvas = new Canvas(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gc = spriteCanvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        for(Entity entity: world.getEntities()) {
            SpriteCP spriteCP = (SpriteCP) entity.getComponent(EntityComponentTypes.SPRITE);
            TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);
            if(spriteCP != null) {
                gc.drawImage(spriteCP.getImage(), transformCP.getCoord().getX()- transformCP.getSize() / 2, transformCP.getCoord().getY()- transformCP.getSize() / 2, spriteCP.getWidth(), spriteCP.getHeight());
            }
        }

        return spriteCanvas;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        gc.clearRect(0,0, gameData.getDisplayWidth(), gameData.getDisplayHeight() );

        TreeMap<Integer, ArrayList<Entity>> layerMap = new TreeMap<>();
        for(Entity entity: world.getEntities()) {
            SpriteCP spriteCP = (SpriteCP) entity.getComponent(EntityComponentTypes.SPRITE);
            if(spriteCP != null) {
                layerMap.putIfAbsent(spriteCP.getLayer(), new ArrayList<Entity>());
                layerMap.get(spriteCP.getLayer()).add(entity);
            }
        }

        for (ArrayList<Entity> layer: layerMap.values()) {
            for (Entity entity: layer) {
                SpriteCP spriteCP = (SpriteCP) entity.getComponent(EntityComponentTypes.SPRITE);
                TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);
                Coordinate coord = transformCP.getCoord();

                if(entity instanceof Player) {
                    gc.drawImage(spriteCP.getImage(), gameData.getDisplayWidth() /2d - spriteCP.getWidth() / 2d, gameData.getDisplayHeight() / 2d - spriteCP.getHeight() / 2d, spriteCP.getWidth(), spriteCP.getHeight());
                } else {
                    gc.drawImage(spriteCP.getImage(), coord.getX() - spriteCP.getWidth() / 2d + gameData.getDisplayWidth() / 2d - world.getPlayerPosition().getX(), coord.getY() - spriteCP.getHeight() / 2d + gameData.getDisplayHeight() / 2d - world.getPlayerPosition().getY(), spriteCP.getWidth(), spriteCP.getHeight());
                }
            }
        }


    }

    @Override
    public void showComponent(Boolean shouldShow) {

    }
}