package dk.lima.common.services;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;

import java.util.concurrent.TimeUnit;


public interface ITimeTask extends Runnable {
    void setGameData(GameData gameData);
    void setWorld(World world);

    long getDelay();
    long getPeriod();
    TimeUnit getTimeUnit();
}
