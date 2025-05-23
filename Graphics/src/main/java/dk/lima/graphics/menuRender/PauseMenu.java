package dk.lima.graphics.menuRender;

import dk.lima.common.data.EGameInputs;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.graphics.menuRender.menuComponent.backToMenuButton;
import dk.lima.graphics.menuRender.menuComponent.exitButton;
import dk.lima.graphics.menuRender.menuComponent.retryButton;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PauseMenu implements IGraphicsService {
    Pane pauseMenuPane;
    private boolean shouldShow = false;
    private boolean wasPausePressed;

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
        Button resetButton = new retryButton(pauseMenuPane,world, gameData, ((gameData.getDisplayWidth() - 250) / 2),(resumeButton.getLayoutY() + 90) ,"Reset");


        Button backToMenuButton = new backToMenuButton(pauseMenuPane,
                world,
                gameData,
                ((gameData.getDisplayWidth() - 250) / 2),
                (resetButton.getLayoutY()+90),
            "Back To Menu!");


        // Exit Button
        Button exitButton = new exitButton(pauseMenuPane, world, gameData, "Exit");

        pauseMenuPane.getChildren().addAll(overlay, text, resumeButton, resetButton, backToMenuButton, exitButton);
        pauseMenuPane.setVisible(shouldShow);

        exitButton.setLayoutX((gameData.getDisplayWidth() - exitButton.getPrefWidth()) / 2);
        exitButton.setLayoutY(backToMenuButton.getLayoutY() + 90);

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
