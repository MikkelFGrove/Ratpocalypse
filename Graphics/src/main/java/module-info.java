import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.services.ITimeTask;
import dk.lima.graphics.HUD.*;
import dk.lima.graphics.gameRender.EntityRenderer;
import dk.lima.graphics.menuRender.*;
import dk.lima.common.services.IGamePluginService;

module Graphics {
    requires Common;
    requires CommonGraphics;
    requires CommonPlayer;
    requires CommonEntityCP;

    requires javafx.graphics;
    requires java.desktop;
    requires javafx.controls;

    uses IGamePluginService;
    uses IGraphicsService;

    provides IGraphicsService with
            EntityRenderer,
            ScoreText,
            TimeText,
            StartMenu,
            PauseMenu;
}


