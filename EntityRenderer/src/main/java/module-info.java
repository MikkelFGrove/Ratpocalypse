import dk.lima.common.graphics.IGraphicsComponent;

module EntityRenderer {
    requires Common;
    requires CommonGraphics;
    requires CommonPlayer;
    requires javafx.graphics;

    provides IGraphicsComponent with
            dk.lima.entityrenderer.EntityRenderer;
}


