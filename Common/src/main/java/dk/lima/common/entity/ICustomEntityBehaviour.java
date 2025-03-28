package dk.lima.common.entity;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;

public interface ICustomEntityBehaviour {
    void process(GameData gameData, World world);
}
