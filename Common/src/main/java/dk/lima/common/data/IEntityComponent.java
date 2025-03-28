package dk.lima.common.data;

public interface IEntityComponent {
    EntityComponentTypes getType();
    void setEntity(Entity entity);
    void process(GameData gameData, World world);
}
