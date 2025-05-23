package dk.lima.graphics.gameRender;

import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entitycomponents.ShapeCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.player.Player;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShapeRenderer implements IGraphicsService {
    private Pane entityPane;
    private Map<Entity, Polygon> polygons;

    @Override
    public Node createComponent(GameData gameData, World world) {
        entityPane = new Pane();
        polygons = new ConcurrentHashMap<>();

        for (Entity entity : world.getEntities()) {
            ShapeCP shapeCP = (ShapeCP) entity.getComponent( EntityComponentTypes.SHAPE);
            if (shapeCP != null) {
                Polygon polygon = new Polygon(shapeCP.getPolygonCoordinates());
                polygon.setFill(Color.rgb(shapeCP.getColor()[0] % 256, shapeCP.getColor()[1] % 256, shapeCP.getColor()[2] % 256));
                polygons.put(entity, polygon);
                entityPane.getChildren().add(polygon);
            }
            
        }

        return entityPane;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        for (Entity polygonEntity : polygons.keySet()) {
            if(!world.getEntities().contains(polygonEntity)){
                Polygon removedPolygon = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                entityPane.getChildren().remove(removedPolygon);
            }
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                ShapeCP shapeCP = (ShapeCP) entity.getComponent(EntityComponentTypes.SHAPE);
                if (shapeCP != null) {
                    polygon = new Polygon(shapeCP.getPolygonCoordinates());
                    polygon.setFill(Color.rgb(shapeCP.getColor()[0] % 256, shapeCP.getColor()[1] % 256, shapeCP.getColor()[2] % 256));
                    polygons.put(entity, polygon);
                    entityPane.getChildren().add(polygon);
                }
            }

            if (polygon != null) {
                TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);
                if (entity instanceof Player) {
                    polygon.setTranslateX(gameData.getDisplayWidth() / 2d);
                    polygon.setTranslateY(gameData.getDisplayHeight() / 2d);
                } else {
                    Coordinate coord = transformCP.getCoord();
                    polygon.setTranslateX(coord.getX() + gameData.getDisplayWidth() / 2d - world.getPlayerPosition().getX());
                    polygon.setTranslateY(coord.getY() + gameData.getDisplayHeight() / 2d - world.getPlayerPosition().getY());
                }

                polygon.setRotate(transformCP.getRotation());
            }
        }
    }

    @Override
    public void showComponent(Boolean shouldShow) {
        entityPane.setVisible(shouldShow);
    }
}
