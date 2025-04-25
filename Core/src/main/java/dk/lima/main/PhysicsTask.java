package dk.lima.main;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IPostEntityProcessingService;

public class PhysicsTask implements Runnable {
    private final GameData gameData;
    private final World world;

    public PhysicsTask(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
    }

    @Override
    public void run() {
        if (gameData.isGameRunning()) {
            for (IEntityProcessingService entityProcessorService : ModuleConfig.getEntityProcessingServices()) {
                entityProcessorService.process(gameData, world);
            }
            for (IPostEntityProcessingService postEntityProcessorService : ModuleConfig.getPostEntityProcessingServices()) {
                postEntityProcessorService.process(gameData, world);
            }
        }
    }
}
