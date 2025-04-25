import dk.lima.TileManager.TileManager;
import dk.lima.common.graphics.IGraphicsService;

module TileManager {
    requires Common;
    requires CommonGraphics;
    requires javafx.graphics;
    provides IGraphicsService with TileManager;
}