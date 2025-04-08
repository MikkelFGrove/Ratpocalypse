package dk.lima.common.entity;

import dk.lima.common.data.EEntityTypes;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();
    private final Map<EntityComponentTypes, IEntityComponent> entityComponentMap;
    private EEntityTypes entityType;

    public Entity() {
        entityComponentMap = new ConcurrentHashMap<>();
    }

    public String getID() {
        return ID.toString();
    }

    public IEntityComponent getComponent(EntityComponentTypes componentType) {
        return entityComponentMap.get(componentType);
    }

    public void addComponent(IEntityComponent component) {
        entityComponentMap.put(component.getType(), component);
    }

    public void removeComponent(EntityComponentTypes componentType) {
        entityComponentMap.remove(componentType);
    }

    public void removeComponent(IEntityComponent component) {
        entityComponentMap.remove(component.getType());
    }

    public Collection<IEntityComponent> getComponents() {
        return entityComponentMap.values();
    }

    public EEntityTypes getEntityType() {
        return entityType;
    }

    public void setEntityType(EEntityTypes entityType) {
        this.entityType = entityType;
    }
}
