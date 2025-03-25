import dk.lima.common.graphics.IGraphicsComponent;
import dk.lima.graphics.gameRender.EntityRenderer;
import dk.lima.graphics.menuRender.StartMenu;

module Graphics {
    exports dk.lima.graphics.menuRender;
    requires Common;
    requires CommonGraphics;
    requires CommonPlayer;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.controls;

    provides IGraphicsComponent with
            EntityRenderer, StartMenu;
}


