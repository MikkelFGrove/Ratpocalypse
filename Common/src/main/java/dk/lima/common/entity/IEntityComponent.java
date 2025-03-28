package dk.lima.common.entity;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;

public interface IEntityComponent {
    EntityComponentTypes getType();
    void setEntity(Entity entity);
    void process(GameData gameData, World world);
}
