import dk.lima.common.graphics.IGraphicsComponent;

module HUD {
    requires Common;
    requires CommonGraphics;
    requires javafx.graphics;

    provides IGraphicsComponent with
            dk.lima.HUD.TimeText,
            dk.lima.HUD.ScoreText;
}


