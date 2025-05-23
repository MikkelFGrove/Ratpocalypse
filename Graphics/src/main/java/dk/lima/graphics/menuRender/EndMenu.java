package dk.lima.graphics.menuRender;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.graphics.menuRender.menuComponent.backToMenuButton;
import dk.lima.graphics.menuRender.menuComponent.exitButton;
import dk.lima.graphics.menuRender.menuComponent.retryButton;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import dk.lima.common.player.Player;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EndMenu implements IGraphicsService {
    static Pane endMenuPane;
    private boolean shouldShow = false;
    private Text highscoreLabel;
    private Text currentscoreLabel;

    @Override
    public Node createComponent(GameData gameData, World world) {
        endMenuPane = new Pane();
        endMenuPane.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        Image background = new Image("/EndScreen_v2.png", gameData.getDisplayWidth() , gameData.getDisplayHeight(), false, false, true);
        endMenuPane.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(4.0);
        dropShadow.setOffsetY(4.0);
        dropShadow.setColor(Color.BLACK);

        Text text = new Text("You were defeated!");
        text.setFont(new Font("Impact", 50));
        text.setEffect(dropShadow);
        text.minHeight(25);
        text.setX((gameData.getDisplayWidth() - text.getLayoutBounds().getWidth()) / 2);
        text.setY(225);
        text.setFill(Color.RED);

        currentscoreLabel = new Text("Your current score: " + gameData.getScore());
        currentscoreLabel.setEffect(dropShadow);
        currentscoreLabel.setFont(new Font("Impact", 35));
        currentscoreLabel.minHeight(25);
        currentscoreLabel.setX((gameData.getDisplayWidth() - currentscoreLabel.getLayoutBounds().getWidth()) / 2);
        currentscoreLabel.setY(320);
        currentscoreLabel.setFill(Color.WHITE);

        highscoreLabel = new Text("Your highscore: " + gameData.getHighscore());
        highscoreLabel.setEffect(dropShadow);
        highscoreLabel.setFont(new Font("Impact", 35));
        highscoreLabel.minHeight(25);
        highscoreLabel.setX((gameData.getDisplayWidth() - highscoreLabel.getLayoutBounds().getWidth()) / 2);
        highscoreLabel.setY(400);
        highscoreLabel.setFill(Color.WHITE);

        //Reset Button
        Button retryButton = new retryButton(endMenuPane, world, gameData, "Try again!");

        //Exit to menu button
        Button backToMenuButton = new backToMenuButton(endMenuPane,world, gameData, "Back To Menu!");

        // Exit Button
        Button exitButton = new exitButton(endMenuPane, world, gameData,"Exit");

        //Button allignment
        VBox buttonBox = new VBox(20);
        buttonBox.setLayoutX((gameData.getDisplayWidth() - retryButton.getPrefWidth()) / 2);
        buttonBox.setLayoutY(gameData.getDisplayHeight() / 2d - 5);
        buttonBox.getChildren().addAll(retryButton, backToMenuButton, exitButton);
        VBox scoreBox = new VBox(20);

        //Text label allignment
        scoreBox.setPrefWidth(gameData.getDisplayWidth());
        scoreBox.setLayoutY(260);
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
