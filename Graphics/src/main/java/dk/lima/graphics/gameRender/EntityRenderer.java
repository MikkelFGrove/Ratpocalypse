package dk.lima.graphics.gameRender;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IGraphicsComponent;
import dk.lima.common.player.Player;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityRenderer implements IGraphicsComponent {
    private Pane entityPane;
    private Map<Entity, Polygon> polygons;

    @Override
    public Node createComponent(GameData gameData, World world) {
        entityPane = new Pane();
        polygons = new ConcurrentHashMap<>();

        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygon.setFill(Color.rgb(entity.getColor()[0] % 256, entity.getColor()[1] % 256, entity.getColor()[2] % 256));
            polygons.put(entity, polygon);
            entityPane.getChildren().add(polygon);
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
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygon.setFill(Color.rgb(entity.getColor()[0] % 256, entity.getColor()[1] % 256, entity.getColor()[2] % 256));
                polygons.put(entity, polygon);
                entityPane.getChildren().add(polygon);
            }

            if (entity instanceof Player) {
                polygon.setTranslateX((double) gameData.getDisplayWidth() / 2);
                polygon.setTranslateY((double) gameData.getDisplayHeight() / 2);
            } else {
                polygon.setTranslateX(entity.getX() + world.getPlayerX());
                polygon.setTranslateY(entity.getY() + world.getPlayerY());
            }

            polygon.setRotate(entity.getRotation());
        }
    }

    @Override
    public void showComponent(Boolean shouldShow) {
        entityPane.setVisible(shouldShow);
    }
}
