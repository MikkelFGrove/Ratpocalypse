package dk.lima.common.enemy;

import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;

public interface IEnemy {
    Entity createEnemy(GameData gameData, World world);
}
