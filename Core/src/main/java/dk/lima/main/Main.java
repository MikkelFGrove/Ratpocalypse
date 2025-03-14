package dk.lima.main;

import dk.lima.TileManager.TileManager;
import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.services.IPostEntityProcessingService;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends Application {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private final TileManager tileManager = new TileManager(gameWindow);

    private Text timeText;
    private Text scoreText;

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());

        timeText = new Text(10, 20, "Time: ");
        timeText.setFont(new Font("Arial", 20));
        timeText.setFill(Color.WHITE);
        gameWindow.getChildren().add(timeText);

        scoreText = new Text(10, 40, "Score: 0");
        scoreText.setFont(new Font("Arial", 20));
        scoreText.setFill(Color.WHITE);
        gameWindow.getChildren().add(scoreText);


        Scene scene = new Scene(gameWindow);
        ModuleConfig.getIInputService().stream().findFirst().ifPresent(service -> {
            scene.setOnKeyPressed(service.getInputHandlerPress(gameData));
        });
        ModuleConfig.getIInputService().stream().findFirst().ifPresent(service -> {
            scene.setOnKeyReleased(service.getInputHandlerRelease(gameData));
        });

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : ModuleConfig.getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygon.setFill(Color.rgb(entity.getColor()[0] % 256, entity.getColor()[1] % 256, entity.getColor()[2] % 256));
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }
        render();
        window.setScene(scene);
        window.setTitle("Ratpocalypse");
        window.show();
    }

    private void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                drawHUD();
                tileManager.draw();
                gameData.getInputs().update();
            }

        }.start();
    }

    private void update() {
        for (IEntityProcessingService entityProcessorService : ModuleConfig.getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : ModuleConfig.getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity polygonEntity : polygons.keySet()) {
            if(!world.getEntities().contains(polygonEntity)){
                Polygon removedPolygon = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                gameWindow.getChildren().remove(removedPolygon);
            }
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }
    }

    private void drawHUD() {
        timeText.setText(String.format("Time: %d:%d", gameData.getDuration().toMinutes() % 60, gameData.getDuration().toSeconds() % 60));
        scoreText.setText(String.format("Score: %d", gameData.getScore()));
    }
}

