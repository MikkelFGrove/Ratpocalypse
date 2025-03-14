package dk.lima.common.enemy;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;

public interface IEnemy {
    public Entity createEnemy(GameData gameData);
}
