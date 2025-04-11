package dk.lima.graphics.menuRender;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.EGameInputs;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.graphics.IMenu;
import dk.lima.common.graphics.MenuType;
import dk.lima.common.services.IGamePluginService;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.time.Duration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PauseMenu implements IMenu {
    Pane pauseMenuPane;
    private boolean shouldShow = false;
    private boolean wasPausePressed;

    @Override
    public MenuType getType() {
        return MenuType.PAUSE;
    }

    @Override
    public Node createComponent(GameData gameData, World world) {
        pauseMenuPane = new Pane();
        pauseMenuPane.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());

        Rectangle overlay = new Rectangle(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        overlay.setFill(new Color(0, 0, 0, 0.5));

        Text text = new Text("Game Paused!");
        text.setFont(new Font("Impact", 120));
        text.setFill(Color.WHITE);
        text.setX((gameData.getDisplayWidth() - text.getLayoutBounds().getWidth()) / 2);
        text.setY((gameData.getDisplayHeight() - text.getLayoutBounds().getHeight()) / 2 - 100);

        Button resumeButton = new Button("Back To It!");
        resumeButton.setPrefWidth(250);
        resumeButton.setPrefHeight(70);
        resumeButton.setStyle("-fx-background-color: #333333; -fx-border-color: #770000; -fx-border-width: 3px;");
        resumeButton.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 24));
        resumeButton.setTextFill(Color.RED);

        resumeButton.setOnMouseEntered(e -> {
            resumeButton.setScaleX(1.03);
            resumeButton.setScaleY(1.04);
            resumeButton.setStyle("-fx-background-color: #550000; -fx-border-color: #ff0000; -fx-border-width: 3px;");
            resumeButton.setCursor(Cursor.HAND);
        });

        resumeButton.setOnMouseExited(e -> {
            resumeButton.setScaleX(1.0);
            resumeButton.setScaleY(1.0);
            resumeButton.setStyle("-fx-background-color: #333333; -fx-border-color: #770000; -fx-border-width: 3px;");
        });

        resumeButton.setLayoutX((gameData.getDisplayWidth() - resumeButton.getPrefWidth()) / 2);
        resumeButton.setLayoutY((gameData.getDisplayHeight() - resumeButton.getPrefHeight()) / 2);

        resumeButton.setOnAction(e -> {
            showComponent(false);
            gameData.setGameRunning(true);
        });

        // Reset Button
        Button resetButton = new Button("Reset");
        resetButton.setPrefWidth(250);
        resetButton.setPrefHeight(70);
        resetButton.setStyle("-fx-background-color: #AA5500; -fx-border-color: #FFAA00; -fx-border-width: 3px;");
        resetButton.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 24));
        resetButton.setTextFill(Color.WHITE);

        resetButton.setOnMouseEntered(e -> {
            resetButton.setScaleX(1.03);
            resetButton.setScaleY(1.04);
            resetButton.setStyle("-fx-background-color: #CC7700; -fx-border-color: #FFDD55; -fx-border-width: 3px;");
            resetButton.setCursor(Cursor.HAND);
        });

        resetButton.setOnMouseExited(e -> {
            resetButton.setScaleX(1.0);
            resetButton.setScaleY(1.0);
            resetButton.setStyle("-fx-background-color: #AA5500; -fx-border-color: #FFAA00; -fx-border-width: 3px;");
        });

        resetButton.setLayoutX((gameData.getDisplayWidth() - resetButton.getPrefWidth()) / 2);
        resetButton.setLayoutY(resumeButton.getLayoutY() + 90);

        resetButton.setOnAction(e -> {
            for (IGamePluginService plugin : getPluginServices()) {
                plugin.stop(gameData, world);
            }

            gameData.setScore(0);
            gameData.setDuration(Duration.ZERO);
            gameData.setCurrentWave(0);
            world.getEntities().clear();
            world.setPlayerPosition(new Coordinate(0, 0));

            for (IGamePluginService plugin : getPluginServices()) {
                plugin.start(gameData, world);
            }

            gameData.setGameRunning(true);
            pauseMenuPane.setVisible(false);
        });

        Button backToMenuButton = new Button("Back To Menu!");
        backToMenuButton.setPrefWidth(250);
        backToMenuButton.setPrefHeight(70);
        backToMenuButton.setStyle("-fx-background-color: #333333; -fx-border-color: #555555; -fx-border-width: 3px;");
        backToMenuButton.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 24));
        backToMenuButton.setTextFill(Color.WHITE);

        backToMenuButton.setOnMouseEntered(e -> {
            backToMenuButton.setScaleX(1.03);
            backToMenuButton.setScaleY(1.04);
            backToMenuButton.setStyle("-fx-background-color: #444444; -fx-border-color: #666666; -fx-border-width: 3px;");
            backToMenuButton.setCursor(Cursor.HAND);
        });

        backToMenuButton.setOnMouseExited(e -> {
            backToMenuButton.setScaleX(1.0);
            backToMenuButton.setScaleY(1.0);
            backToMenuButton.setStyle("-fx-background-color: #333333; -fx-border-color: #555555; -fx-border-width: 3px;");
        });

        backToMenuButton.setLayoutX((gameData.getDisplayWidth() - backToMenuButton.getPrefWidth()) / 2);
        backToMenuButton.setLayoutY(resetButton.getLayoutY() + 90);

        backToMenuButton.setOnAction(e -> {
            for (IGamePluginService plugin : getPluginServices()) {
                plugin.stop(gameData, world);
            }

            gameData.setScore(0);
            gameData.setDuration(Duration.ZERO);
            gameData.setCurrentWave(0);
            world.getEntities().clear();
            world.setPlayerPosition(new Coordinate(0, 0));

            for (IGamePluginService plugin : getPluginServices()) {
                plugin.start(gameData, world);
            }

            gameData.setGameRunning(false);
            StartMenu startMenu = new StartMenu();
            startMenu.showComponent(true);
            pauseMenuPane.setVisible(false);
        });

        // Exit Button
        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(250);
        exitButton.setPrefHeight(70);
        exitButton.setStyle("-fx-background-color: #550000; -fx-border-color: #ff0000; -fx-border-width: 3px;");
        exitButton.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 24));
        exitButton.setTextFill(Color.WHITE);

        exitButton.setOnMouseEntered(e -> {
            exitButton.setScaleX(1.03);
            exitButton.setScaleY(1.04);
            exitButton.setStyle("-fx-background-color: #770000; -fx-border-color: #ff5555; -fx-border-width: 3px;");
            exitButton.setCursor(Cursor.HAND);
        });

        exitButton.setOnMouseExited(e -> {
            exitButton.setScaleX(1.0);
            exitButton.setScaleY(1.0);
            exitButton.setStyle("-fx-background-color: #550000; -fx-border-color: #ff0000; -fx-border-width: 3px;");
        });

        exitButton.setLayoutX((gameData.getDisplayWidth() - exitButton.getPrefWidth()) / 2);
        exitButton.setLayoutY(backToMenuButton.getLayoutY() + 90);

        exitButton.setOnAction(e -> {
            Platform.exit();
        });

        pauseMenuPane.getChildren().addAll(overlay, text, resumeButton, resetButton, backToMenuButton, exitButton);
        pauseMenuPane.setVisible(shouldShow);

        return pauseMenuPane;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        boolean isPausePressed = gameData.getInputs().isDown(EGameInputs.PAUSE);

        if (isPausePressed && !wasPausePressed) {
            pauseMenuPane.setVisible(!pauseMenuPane.isVisible());
            gameData.setGameRunning(!pauseMenuPane.isVisible());
        }
        wasPausePressed = isPausePressed;

    }

    @Override
    public void showComponent(Boolean shouldShow) {
        pauseMenuPane.setVisible(shouldShow);
    }

    public static Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}
