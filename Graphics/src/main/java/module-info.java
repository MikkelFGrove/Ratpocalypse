import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.graphics.HUD.*;
import dk.lima.graphics.gameRender.*;
import dk.lima.graphics.menuRender.*;

module Graphics {
    requires Common;
    requires CommonGraphics;
    requires CommonPlayer;
    requires PathfindingComponent;

    requires javafx.graphics;
    requires java.desktop;
    requires javafx.controls;
    requires CommonEntityCP;

    uses IGamePluginService;
    uses IGraphicsService;


    provides IGraphicsService with
            SpriteRenderer,
            ShapeRenderer,
            HealthRenderer,
            DebugRenderer,
            ScoreText,
            TimeText,
            HealthBar,
            WaveCounter,
            StartMenu,
            PauseMenu,
            EndMenu;
}




