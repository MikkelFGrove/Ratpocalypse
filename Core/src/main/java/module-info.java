import dk.lima.common.graphics.IGraphicsComponent;
import dk.lima.common.input.IInputSPI;

module Core {
    requires javafx.graphics;
    requires Common;
    requires CommonInput;
    requires CommonPlayer;
    requires CommonGraphics;
    requires TileManager;

    opens dk.lima.main to javafx.graphics;
    uses dk.lima.common.services.IGamePluginService;
    uses dk.lima.common.services.IEntityProcessingService;
    uses dk.lima.common.services.IPostEntityProcessingService;
    uses IGraphicsComponent;
    uses IInputSPI;
    uses dk.lima.common.services.IWaveSpawner;
}