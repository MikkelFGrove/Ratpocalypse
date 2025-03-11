import dk.lima.common.input.IInputSPI;

module Core {
    requires javafx.graphics;
    requires Common;
    requires CommonInput;
    requires TileManager;

    opens dk.lima.main to javafx.graphics;
    uses dk.lima.common.services.IGamePluginService;
    uses dk.lima.common.services.IEntityProcessingService;
    uses dk.lima.common.services.IPostEntityProcessingService;
    uses IInputSPI;
}