package dk.lima.main;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IGraphicsComponent;
import dk.lima.common.player.Player;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.services.IPostEntityProcessingService;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Pane gameWindow = new Pane();
    private List<IGraphicsComponent> graphicsComponents;

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        Scene scene = new Scene(gameWindow);

        ModuleConfig.getIInputService().stream().findFirst().ifPresent(service -> {
            scene.setOnKeyPressed(service.getInputHandlerPress(gameData));
        });
        ModuleConfig.getIInputService().stream().findFirst().ifPresent(service -> {
            scene.setOnKeyReleased(service.getInputHandlerRelease(gameData));
        });

        graphicsComponents = new ArrayList<>(ModuleConfig.getGraphicComponents());
        for (IGraphicsComponent graphicsComponent : graphicsComponents) {
            gameWindow.getChildren().add(graphicsComponent.createComponent(gameData, world));
        }

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : ModuleConfig.getPluginServices()) {
            iGamePlugin.start(gameData, world);
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
                updateGraphics();
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

    private void updateGraphics() {
        for (IGraphicsComponent graphicsComponent : graphicsComponents) {
            graphicsComponent.updateComponent(gameData, world);
        }
    }
}

