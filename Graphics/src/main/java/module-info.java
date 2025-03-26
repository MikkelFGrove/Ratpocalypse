import dk.lima.common.graphics.IGraphicsService;
import dk.lima.graphics.HUD.*;
import dk.lima.graphics.gameRender.EntityRenderer;
import dk.lima.graphics.menuRender.*;

module Graphics {
    requires Common;
    requires CommonGraphics;
    requires CommonPlayer;

    requires javafx.graphics;
    requires java.desktop;
    requires javafx.controls;

    provides IGraphicsService with
            EntityRenderer,
            ScoreText,
            TimeText,
            StartMenu;
}


