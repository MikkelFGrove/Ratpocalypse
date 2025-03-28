package dk.lima.graphics.menuRender;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IMenu;
import dk.lima.common.graphics.MenuType;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.*;

public class StartMenu implements IMenu {
    static Pane startMenuPane;
    @Override
    public MenuType getType() {
        return MenuType.START;
    }

    @Override
    public Node createComponent(GameData gameData, World world) {
        startMenuPane = new Pane();
        startMenuPane.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        Text text = new Text("Ratpocalypse");
        text.setFont(new Font("Impact", 120));
        text.minHeight(200);
        text.setX((gameData.getDisplayWidth() - text.getLayoutBounds().getWidth()) / 2);
        text.setY((gameData.getDisplayHeight() - text.getLayoutBounds().getHeight()) / 2 - 100);
        Button button = new Button("Start!");

        button.setPrefWidth(250);
        button.setPrefHeight(70);

        button.setStyle("-fx-background-color: #333333; -fx-border-color: #770000; -fx-border-width: 3px;");
        button.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 24));
        button.setTextFill(Color.RED);

        button.setOnMouseEntered(e -> {
            button.setScaleX(1.03);
            button.setScaleY(1.04);
            button.setStyle("-fx-background-color: #550000; -fx-border-color: #ff0000; -fx-border-width: 3px;");
            button.setCursor(Cursor.HAND);
        });

        button.setOnMouseExited(e -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
            button.setStyle("-fx-background-color: #333333; -fx-border-color: #770000; -fx-border-width: 3px;");
        });


        button.setLayoutX((gameData.getDisplayHeight() - text.getLayoutBounds().getHeight()) / 2 + 20);
        button.widthProperty().addListener((obs, oldVal, newVal) ->
                button.setLayoutX((gameData.getDisplayWidth() - newVal.doubleValue()) / 2)
        );
        button.heightProperty().addListener((obs, oldVal, newVal) ->
                button.setLayoutY((gameData.getDisplayHeight() - newVal.doubleValue()) / 2 + 20)
        );

        button.setOnAction(e -> {gameData.setGameRunning(true); showComponent(false);});


        text.setFill(Color.WHITE);

        Image background = new Image("/SplashArtStartScreen.png", gameData.getDisplayWidth() , gameData.getDisplayHeight(), false, false, true);
        startMenuPane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        startMenuPane.getChildren().add(text);
        startMenuPane.getChildren().add(button);
        return startMenuPane;

    }

    @Override
    public void updateComponent(GameData gameData, World world) {
    }

    @Override
    public void showComponent(Boolean shouldShow) {
        startMenuPane.setVisible(shouldShow);
    }
}
