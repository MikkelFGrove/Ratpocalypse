import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.services.ITimeTask;
import dk.lima.graphics.HUD.*;
import dk.lima.graphics.gameRender.EntityRenderer;
import dk.lima.graphics.gameRender.SpriteRenderer;
import dk.lima.graphics.menuRender.*;
import dk.lima.common.services.IGamePluginService;

module Graphics {
    requires Common;
    requires CommonGraphics;
    requires CommonPlayer;

    requires javafx.graphics;
    requires java.desktop;
    requires javafx.controls;
    requires CommonEntityCP;

    uses IGamePluginService;
    uses IGraphicsService;


    provides IGraphicsService with
            EntityRenderer,
            SpriteRenderer,
            ScoreText,
            TimeText,
            StartMenu,
            PauseMenu,
            EndMenu;

}




