package dk.lima.graphics.menuRender.menuComponent;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IGamePluginService;
import dk.lima.graphics.menuRender.StartMenu;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.Pane;

import java.time.Duration;
import java.util.Collection;
import java.util.ServiceLoader;

import dk.lima.common.services.IGamePluginService;

import static java.util.stream.Collectors.toList;


public class backToMenuButton extends Button {
    public backToMenuButton(Pane menu, World world, GameData gameData, double layoutX, double layoutY, String text) {
        setMenu(menu, world, gameData, text);

        this.setLayoutX(layoutX);
        this.setLayoutY(layoutY);

    }

    public backToMenuButton(Pane menu, World world, GameData gameData, String text) {
        setMenu(menu, world, gameData, text);
    }


    public void setMenu(Pane menu, World world, GameData gameData, String text) {

        this.setText(text);
        this.setPrefWidth(250);
        this.setPrefHeight(70);
        this.setStyle("-fx-background-color: #333333; -fx-border-color: #555555; -fx-border-width: 3px;");
        this.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 24));
        this.setTextFill(Color.WHITE);

        this.setOnMouseEntered(e -> {
            this.setScaleX(1.03);
            this.setScaleY(1.04);
            this.setStyle("-fx-background-color: #444444; -fx-border-color: #666666; -fx-border-width: 3px;");
            this.setCursor(Cursor.HAND);
        });

        this.setOnMouseExited(e -> {
            this.setScaleX(1.0);
            this.setScaleY(1.0);
            this.setStyle("-fx-background-color: #333333; -fx-border-color: #555555; -fx-border-width: 3px;");
        });

        this.setOnAction(e -> {
            for (IGamePluginService plugin : getPluginServices()) {
                plugin.stop(gameData, world);
            }

            gameData.setScore(0);
            gameData.setDuration(Duration.ZERO);
            world.getEntities().clear();
            world.setPlayerPosition(new Coordinate(0, 0));

            for (IGamePluginService plugin : getPluginServices()) {
                plugin.start(gameData, world);
            }

            gameData.setGameRunning(false);
            StartMenu startMenu = new StartMenu();
            startMenu.showComponent(true);
            menu.setVisible(false);
        });
    }



    public static Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
