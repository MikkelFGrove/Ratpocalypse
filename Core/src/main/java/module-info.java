module Core {
    requires javafx.graphics;
    requires Common;
    opens dk.lima.main to javafx.graphics;
    uses dk.lima.common.services.IGamePluginService;
    uses dk.lima.common.services.IEntityProcessingService;
    uses dk.lima.common.services.IPostEntityProcessingService;

}