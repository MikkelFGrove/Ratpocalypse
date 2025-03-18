import dk.lima.TileManager.TileManager;
import dk.lima.common.graphics.IGraphicsComponent;

module TileManager {
    requires Common;
    requires CommonGraphics;
    requires javafx.graphics;

    provides IGraphicsComponent with TileManager;
}