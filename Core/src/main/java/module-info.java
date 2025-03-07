module Core {
    requires javafx.graphics;
    requires Common;
    requires CommonInput;

    opens dk.lima.main to javafx.graphics;
    uses dk.lima.common.services.IGamePluginService;
    uses dk.lima.common.services.IEntityProcessingService;
    uses dk.lima.common.services.IPostEntityProcessingService;
    uses dk.lima.commonInput.IInputSPI;
}