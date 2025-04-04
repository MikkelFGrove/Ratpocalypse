package dk.lima.graphics.menuRender;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IMenu;
import dk.lima.common.graphics.MenuType;
import dk.lima.common.services.IGamePluginService;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
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
        endMenuPane.setStyle("-fx-background-color: black;");

        Text text = new Text("You were defeated!");
        text.setFont(new Font("Impact", 50));
        text.minHeight(25);
        text.setX((gameData.getDisplayWidth() - text.getLayoutBounds().getWidth()) / 2);
        text.setY(150);
        text.setFill(Color.WHITE);
        Button retryButton = new Button("Retry");

        currentscoreLabel = new Text("Your current score: " + gameData.getScore());
        currentscoreLabel.setFont(new Font("Impact", 35));
        currentscoreLabel.minHeight(25);
        currentscoreLabel.setX((gameData.getDisplayWidth() - currentscoreLabel.getLayoutBounds().getWidth()) / 2);
        currentscoreLabel.setY(320);
        currentscoreLabel.setFill(Color.WHITE);

        highscoreLabel = new Text("Your highscore:\n" + gameData.getHighscore());
        highscoreLabel.setFont(new Font("Impact", 35));
        highscoreLabel.minHeight(25);
        highscoreLabel.setX((gameData.getDisplayWidth() - highscoreLabel.getLayoutBounds().getWidth()) / 2);
        highscoreLabel.setY(400);
        highscoreLabel.setFill(Color.WHITE);

        retryButton.setPrefWidth(250);
        retryButton.setPrefHeight(70);

        retryButton.setStyle("-fx-background-color: #333333; -fx-border-color: #666655; -fx-border-width: 3px;");
        retryButton.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 24));
        retryButton.setTextFill(Color.GREEN);

        retryButton.setOnMouseEntered( e-> {
            retryButton.setScaleX(1.03);
            retryButton.setScaleY(1.04);
            retryButton.setStyle("-fx-background-color: #555555; -fx-border-color: #00FF00; -fx-border-width: 3px;");
            retryButton.setCursor(Cursor.HAND);
        });


        retryButton.setOnMouseExited(e -> {
            retryButton.setScaleX(1.0);
            retryButton.setScaleY(1.0);
            retryButton.setStyle("-fx-background-color: #555555; -fx-border-color: #006400; -fx-border-width: 3px;");
        });

        retryButton.setLayoutX((gameData.getDisplayHeight() - text.getLayoutBounds().getHeight()) / 2 + 20);

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
            world.setPlayerX(0);
            world.setPlayerY(0);

            for (IGamePluginService plugin : getPluginServices()) {
                plugin.start(gameData, world);
            }

            gameData.setGameRunning(true);
            endMenuPane.setVisible(false);
        });

        endMenuPane.getChildren().add(text);
        endMenuPane.getChildren().add(highscoreLabel);
        endMenuPane.getChildren().add(currentscoreLabel);
        endMenuPane.getChildren().add(retryButton);


        endMenuPane.setVisible(shouldShow);
        return endMenuPane;
    }


    @Override
    public void updateComponent(GameData gameData, World world) {
        if (world.getEntities(Player.class).isEmpty()) {
            showComponent(true);
            endMenuPane.setVisible(true);
            highscoreLabel.setText("Your highscore:\n" + gameData.getHighscore());
        }
    }

    @Override
    public void showComponent(Boolean shouldShow) {

    }


    public static Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
