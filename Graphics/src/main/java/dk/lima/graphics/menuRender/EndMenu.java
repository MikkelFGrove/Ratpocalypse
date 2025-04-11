package dk.lima.graphics.menuRender;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IMenu;
import dk.lima.common.graphics.MenuType;
import dk.lima.common.services.IGamePluginService;
import dk.lima.graphics.menuRender.menuComponent.backToMenuButton;
import dk.lima.graphics.menuRender.menuComponent.exitButton;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.Cursor;
import dk.lima.common.player.Player;

import java.time.Duration;
import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EndMenu implements IMenu {
    static Pane endMenuPane;
    private boolean shouldShow = false;
    private Text highscoreLabel;
    private Text currentscoreLabel;

    @Override
    public MenuType getType() {
        return MenuType.END;
    }

    @Override
    public Node createComponent(GameData gameData, World world) {
        endMenuPane = new Pane();
        endMenuPane.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        Image background = new Image("/SplashArtStartScreen.png", gameData.getDisplayWidth() , gameData.getDisplayHeight(), false, false, true);
        endMenuPane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        Text text = new Text("You were defeated!");
        text.setFont(new Font("Impact", 50));
        text.minHeight(25);
        text.setX((gameData.getDisplayWidth() - text.getLayoutBounds().getWidth()) / 2);
        text.setY(150);
        text.setFill(Color.WHITE);
        Button retryButton = new Button("Try Again");

        currentscoreLabel = new Text("Your current score: " + gameData.getScore());
        currentscoreLabel.setFont(new Font("Impact", 35));
        currentscoreLabel.minHeight(25);
        currentscoreLabel.setX((gameData.getDisplayWidth() - currentscoreLabel.getLayoutBounds().getWidth()) / 2);
        currentscoreLabel.setY(320);
        currentscoreLabel.setFill(Color.WHITE);

        highscoreLabel = new Text("Your highscore: " + gameData.getHighscore());
        highscoreLabel.setFont(new Font("Impact", 35));
        highscoreLabel.minHeight(25);
        highscoreLabel.setX((gameData.getDisplayWidth() - highscoreLabel.getLayoutBounds().getWidth()) / 2);
        highscoreLabel.setY(400);
        highscoreLabel.setFill(Color.WHITE);

        //Reset Button
        retryButton.setPrefWidth(250);
        retryButton.setPrefHeight(70);
        retryButton.setStyle("-fx-background-color: #AA5500; -fx-border-color: #FFAA00; -fx-border-width: 3px;");
        retryButton.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 24));
        retryButton.setTextFill(Color.WHITE);

        retryButton.setOnMouseEntered( e-> {
            retryButton.setScaleX(1.03);
            retryButton.setScaleY(1.04);
            retryButton.setStyle("-fx-background-color: #CC7700; -fx-border-color: #FFDD55; -fx-border-width: 3px;");
            retryButton.setCursor(Cursor.HAND);
        });

        retryButton.setOnMouseExited(e -> {
            retryButton.setScaleX(1.0);
            retryButton.setScaleY(1.0);
            retryButton.setStyle("-fx-background-color: #AA5500; -fx-border-color: #FFAA00; -fx-border-width: 3px;");
        });

        retryButton.widthProperty().addListener((obs, oldVal, newVal) ->
                retryButton.setLayoutX((gameData.getDisplayWidth() - newVal.doubleValue()) / 2)
        );
        retryButton.heightProperty().addListener((obs, oldVal, newVal) ->
                retryButton.setLayoutY((gameData.getDisplayHeight() - newVal.doubleValue()) / 2 + 250)
        );

        retryButton.setOnAction(e -> {
            for (IGamePluginService plugin : getPluginServices()) {
                plugin.stop(gameData, world);
            }

            gameData.setScore(0);
            gameData.setDuration(Duration.ZERO);
            world.getEntities().clear();
            world.setPlayerPosition(new Coordinate(0,0));

            for (IGamePluginService plugin : getPluginServices()) {
                plugin.start(gameData, world);
            }

            gameData.setGameRunning(true);
            endMenuPane.setVisible(false);
        });

        //Exit to menu button
        Button backToMenuButton = new backToMenuButton(endMenuPane,world, gameData, "Back To Menu!");

        // Exit Button
        Button exitButton = new exitButton(endMenuPane, world, gameData,"Exit");

        //Button allignment
        VBox buttonBox = new VBox(20);
        buttonBox.setLayoutX((gameData.getDisplayWidth() - retryButton.getPrefWidth()) / 2);
        buttonBox.setLayoutY(gameData.getDisplayHeight() / 2 + 100);
        buttonBox.getChildren().addAll(retryButton, backToMenuButton, exitButton);
        VBox scoreBox = new VBox(20);

        //Text label allignment
        scoreBox.setPrefWidth(gameData.getDisplayWidth());
        scoreBox.setLayoutY(280);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.getChildren().addAll(currentscoreLabel, highscoreLabel);

        endMenuPane.getChildren().addAll(text, buttonBox, scoreBox);

        endMenuPane.setVisible(shouldShow);
        return endMenuPane;
    }


    @Override
    public void updateComponent(GameData gameData, World world) {
        if (world.getEntities(Player.class).isEmpty()) {
            gameData.setGameRunning(false);
            showComponent(true);
            endMenuPane.setVisible(true);
            highscoreLabel.setText("Your highscore: " + gameData.getHighscore());
            currentscoreLabel.setText("Current Score: " + gameData.getScore());
        }
    }

    @Override
    public void showComponent(Boolean shouldShow) {

    }


    public static Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
