package dk.lima.graphics.menuRender.menuComponent;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class exitButton extends Button {

    public exitButton(Pane menuPane, World world, GameData gameData, String text) {
        this.setPrefWidth(250);
        this.setPrefHeight(70);
        this.setStyle("-fx-background-color: #550000; -fx-border-color: #ff0000; -fx-border-width: 3px;");
        this.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 24));
        this.setTextFill(Color.WHITE);

        this.setText(text);

        this.setOnMouseEntered(e -> {
            this.setScaleX(1.03);
            this.setScaleY(1.04);
            this.setStyle("-fx-background-color: #770000; -fx-border-color: #ff5555; -fx-border-width: 3px;");
            this.setCursor(Cursor.HAND);
        });

        this.setOnMouseExited(e -> {
            this.setScaleX(1.0);
            this.setScaleY(1.0);
            this.setStyle("-fx-background-color: #550000; -fx-border-color: #ff0000; -fx-border-width: 3px;");
        });



        this.setOnAction(e -> {
            Platform.exit();
        });

    }

}
