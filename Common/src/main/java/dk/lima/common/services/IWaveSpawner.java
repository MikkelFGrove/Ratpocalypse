package dk.lima.common.services;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;


public interface IWaveSpawner {
    void update(GameData gameData, World world);
}
