import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.input.IInputSPI;
import dk.lima.common.services.ITimeTask;

module Core {
    requires javafx.graphics;
    requires Common;
    requires CommonInput;
    requires CommonPlayer;
    requires CommonGraphics;
    requires TileManager;
    requires Graphics;

    opens dk.lima.main to javafx.graphics;
    uses dk.lima.common.services.IGamePluginService;
    uses dk.lima.common.services.IEntityProcessingService;
    uses dk.lima.common.services.IPostEntityProcessingService;
    uses IGraphicsService;
    uses IInputSPI;
    uses ITimeTask;
}