package dk.lima.common.services;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;

public interface IGamePluginService {
    /**
     * Method for starting the plug-in
     *
     * Precondition: Plug-in must not be started
     * Postcondition: Plug-in must be started
     *
     * @param gameData Data about the screen size and allowed keys
     * @param world Data about the entities in the world
     * @throws
     */
    void start(GameData gameData, World world);

    /**
     * Method for stopping the plug-in
     *
     * Precondition: Plug-in must be running
     * Postcondition: Plug-in must be stopped
     *
     * @param gameData Data about the screen size and allowed keys
     * @param world Data about the entities in the world
     * @throws
     */
    void stop(GameData gameData, World world);
}
