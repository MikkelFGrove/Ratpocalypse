package dk.lima.graphics.menuRender;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IMenu;
import dk.lima.common.graphics.MenuType;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
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
        Image background = new Image("/StartScreen_v5.png", gameData.getDisplayWidth() , gameData.getDisplayHeight(), false, false, true);
        //<editor-fold desc="Title Text">
        //Text titleText = new Text("Ratpocalypse");
        //titleText.setFont(new Font("Impact", 120));
        //titleText.setFill(Color.WHITE);
        //titleText.minHeight(200);
        //titleText.setX((gameData.getDisplayWidth() - titleText.getLayoutBounds().getWidth()) / 2);
        //titleText.setY((gameData.getDisplayHeight() - titleText.getLayoutBounds().getHeight()) / 2 - 100);
        //</editor-fold>

        //<editor-fold desc="Help Title">
        Text helpTitle = new Text("Help menu");
        helpTitle.setFont(new Font("Impact", 70));
        helpTitle.setFill(Color.WHITE);
        helpTitle.minHeight(200);
        helpTitle.setX((gameData.getDisplayWidth() - helpTitle.getLayoutBounds().getWidth()) / 2);
        helpTitle.setY((gameData.getDisplayHeight() - helpTitle.getLayoutBounds().getHeight()) / 2 - 250);
        //</editor-fold>

        //<editor-fold desc="WASD Text">
        Text wasdText = new Text("Use WASD or arrow keys to control the player!");
        wasdText.setFont(new Font("Arial", 25));
        wasdText.setFill(Color.WHITE);
        wasdText.minHeight(200);
        wasdText.setX((gameData.getDisplayWidth() - wasdText.getLayoutBounds().getWidth()) / 2);
        wasdText.setY((gameData.getDisplayHeight() - wasdText.getLayoutBounds().getHeight()) / 2 - 180);
        //</editor-fold>

        //<editor-fold desc="Mouse Text">
        Text mouseText = new Text("Use mouse to aim\nShoot (Spacebar or Left Mouse Button)");
        mouseText.setFont(new Font("Arial", 25));
        mouseText.setTextAlignment(TextAlignment.CENTER);
        mouseText.setFill(Color.WHITE);
        mouseText.minHeight(200);
        mouseText.setX((gameData.getDisplayWidth() - mouseText.getLayoutBounds().getWidth()) / 2);
        mouseText.setY((gameData.getDisplayHeight() - mouseText.getLayoutBounds().getHeight()) / 2 + 30);
        //</editor-fold>

        //<editor-fold desc="Help Button">
        Button helpButton = new Button("HELP ME!");
        int helpButtonWidth = 120;
        int helpButtonHeight = 100;
        helpButton.setPrefWidth(helpButtonWidth);
        helpButton.setPrefWidth(helpButtonHeight);
        helpButton.setStyle("-fx-background-color: #333333; -fx-border-color: #172a4e; -fx-border-width: 3px;");
        helpButton.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 18));
        helpButton.setTextFill(Color.SKYBLUE);

        helpButton.setOnMouseEntered(e -> {
            helpButton.setScaleX(1.03);
            helpButton.setScaleY(1.04);
            helpButton.setStyle("-fx-background-color: #103642; -fx-border-color: #0642c3; -fx-border-width: 3px;");
            helpButton.setCursor(Cursor.HAND);
        });
        helpButton.setOnMouseExited(e -> {
            helpButton.setScaleX(1.0);
            helpButton.setScaleY(1.0);
            helpButton.setStyle("-fx-background-color: #333333; -fx-border-color: #172a4e; -fx-border-width: 3px;");
        });

        helpButton.setLayoutX((double) gameData.getDisplayWidth() /2 - (double) helpButtonWidth /2 + 8);
        helpButton.setLayoutY((double) gameData.getDisplayHeight() /2 - (double) helpButtonHeight /2 + 100);
        //</editor-fold>

        //<editor-fold desc="Start Button">
        Button startButton = new Button("START!");

        int startButtonWidth = 250;
        int startButtonHeight = 70;

        startButton.setPrefWidth(startButtonWidth);
        startButton.setPrefHeight(startButtonHeight);

        startButton.setStyle("-fx-background-color: #333333; -fx-border-color: #770000; -fx-border-width: 3px;");
        startButton.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 30));
        startButton.setTextFill(Color.RED);

        startButton.setOnMouseEntered(e -> {
            startButton.setScaleX(1.03);
            startButton.setScaleY(1.04);
            startButton.setStyle("-fx-background-color: #550000; -fx-border-color: #ff0000; -fx-border-width: 3px;");
            startButton.setCursor(Cursor.HAND);
        });
        startButton.setOnMouseExited(e -> {
            startButton.setScaleX(1.0);
            startButton.setScaleY(1.0);
            startButton.setStyle("-fx-background-color: #333333; -fx-border-color: #770000; -fx-border-width: 3px;");
        });

        startButton.setLayoutX((double) gameData.getDisplayWidth() /2 - (double) startButtonWidth /2);
        startButton.setLayoutY((double) gameData.getDisplayHeight() /2 - (double) startButtonHeight /2 - 20);
        //</editor-fold>

        //<editor-fold desc="Back Button">
        Button backButton = new Button("Back");
        int backButtonWidth = 120;
        int backButtonHeight = 100;
        backButton.setPrefWidth(backButtonWidth);
        backButton.setPrefWidth(backButtonHeight);
        backButton.setStyle("-fx-background-color: #333333; -fx-border-color: #172a4e; -fx-border-width: 3px;");
        backButton.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 18));
        backButton.setTextFill(Color.SKYBLUE);
        backButton.setOnMouseEntered(e -> {
            backButton.setScaleX(1.03);
            backButton.setScaleY(1.04);
            backButton.setStyle("-fx-background-color: #103642; -fx-border-color: #0642c3; -fx-border-width: 3px;");
            backButton.setCursor(Cursor.HAND);
        });
        backButton.setOnMouseExited(e -> {
            backButton.setScaleX(1.0);
            backButton.setScaleY(1.0);
            backButton.setStyle("-fx-background-color: #333333; -fx-border-color: #172a4e; -fx-border-width: 3px;");
        });
        backButton.setLayoutX((double) gameData.getDisplayWidth() /2 - (double) backButtonWidth /2);
        backButton.setLayoutY((double) gameData.getDisplayHeight() /2 - (double) backButtonHeight /2 + 250);
        //</editor-fold>

        //<editor-fold desc="WASD Image">
        Image wasdImage = new Image("/WASDImage.png");
        ImageView wasdImageView = new ImageView(wasdImage);
        wasdImageView.setFitWidth(wasdImageView.getLayoutBounds().getWidth() / 3);
        wasdImageView.setFitHeight(wasdImageView.getLayoutBounds().getHeight() / 3);
        wasdImageView.setX(((double) gameData.getDisplayWidth() / 2 - wasdImageView.getLayoutBounds().getCenterX()));
        wasdImageView.setY((double) gameData.getDisplayHeight() / 2 - wasdImageView.getLayoutBounds().getHeight() / 2 - 100);
        //</editor-fold>

        //<editor-fold desc="Mouse Image">
        Image mouseImage = new Image("/MouseIcon.png");
        ImageView mouseImageView = new ImageView(mouseImage);
        mouseImageView.setFitWidth(mouseImageView.getLayoutBounds().getWidth() );
        mouseImageView.setFitHeight(mouseImageView.getLayoutBounds().getHeight() );
        mouseImageView.setX(((double) gameData.getDisplayWidth() / 2 - mouseImageView.getLayoutBounds().getCenterX()) - 100);
        mouseImageView.setY((double) gameData.getDisplayHeight() / 2 - mouseImageView.getLayoutBounds().getHeight() / 2 + 100);
        //</editor-fold>

        //<editor-fold desc="Spacebar Image">
        Image spacebarImage = new Image("/Spacebar.png");
        ImageView spacebarImageView = new ImageView(spacebarImage);
        spacebarImageView.setFitWidth(spacebarImageView.getLayoutBounds().getWidth() / 1.1);
        spacebarImageView.setFitHeight(spacebarImageView.getLayoutBounds().getHeight() / 1.1 );
        spacebarImageView.setX(((double) gameData.getDisplayWidth() / 2 - spacebarImageView.getLayoutBounds().getCenterX()) + 120);
        spacebarImageView.setY((double) gameData.getDisplayHeight() / 2 - spacebarImageView.getLayoutBounds().getHeight() / 2 + 115);
        //</editor-fold>

        Rectangle overlay = new Rectangle(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        overlay.setFill(new Color(0, 0, 0, 0.7));

        backButton.setOnAction(e -> {
            startMenuPane.getChildren().clear();
            //startMenuPane.getChildren().add(titleText);
            startMenuPane.getChildren().add(startButton);
            startMenuPane.getChildren().add(helpButton);
        });

        helpButton.setOnAction(e -> {
            startMenuPane.getChildren().clear();
            startMenuPane.getChildren().add(overlay);
            startMenuPane.getChildren().add(wasdText);
            startMenuPane.getChildren().add(mouseText);
            startMenuPane.getChildren().add(spacebarImageView);
            startMenuPane.getChildren().add(mouseImageView);
            startMenuPane.getChildren().add(wasdImageView);
            startMenuPane.getChildren().add(backButton);
            startMenuPane.getChildren().add(helpTitle);
        });

        startButton.setOnAction(e -> {gameData.setGameRunning(true); showComponent(false);});

        startMenuPane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        //startMenuPane.getChildren().add(titleText);
        startMenuPane.getChildren().add(startButton);
        startMenuPane.getChildren().add(helpButton);
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
