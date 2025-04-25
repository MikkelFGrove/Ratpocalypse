package dk.lima.main;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.graphics.IMenu;
import dk.lima.common.input.IInputSPI;
import dk.lima.common.services.IGamePluginService;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main extends Application {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Pane gameWindow = new Pane();
    private static ScheduledExecutorService physicsExecutor;
    private List<IGraphicsService> graphicsServices;
    private List <IMenu> menuComponents;

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        Scene scene = new Scene(gameWindow);

        for (IInputSPI inputSPI : ModuleConfig.getIInputService()) {
            scene.addEventHandler(inputSPI.getInputEvent(), inputSPI.getInputHandler(gameData));
        }

        graphicsServices = new ArrayList<>(ModuleConfig.getGraphicComponents());
        for (IGraphicsService graphicsComponent : graphicsServices) {
            gameWindow.getChildren().add(graphicsComponent.createComponent(gameData, world));
        }

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : ModuleConfig.getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }

        physicsExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        });

        startGame();
        window.setResizable(false);
        window.setScene(scene);
        window.setTitle("Ratpocalypse");
        window.show();
    }

    private void startGame() {
        // Tickrate : 100Hz or 100 updates per frame
        physicsExecutor.scheduleAtFixedRate(new PhysicsTask(gameData, world), 0, 10, TimeUnit.MILLISECONDS);
        render();
    }

    private void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameData.getInputs().update();
                updateGraphics();
            }
        }.start();
    }

    private void updateGraphics() {
        for (IGraphicsService graphicsService : graphicsServices) {
            graphicsService.updateComponent(gameData, world);
        }
    }
}

