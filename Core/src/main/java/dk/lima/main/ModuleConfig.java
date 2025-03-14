package dk.lima.main;

import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.services.IGamePluginService;
import dk.lima.common.services.IPostEntityProcessingService;
import dk.lima.common.input.IInputSPI;
import dk.lima.common.services.IWaveSpawner;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class ModuleConfig {
    public static Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    public static Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    public static Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    public static Collection<? extends IInputSPI> getIInputService() {
        return ServiceLoader.load(IInputSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    public static Collection<? extends IWaveSpawner> getIWaveSpawner() {
        return ServiceLoader.load(IWaveSpawner.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
