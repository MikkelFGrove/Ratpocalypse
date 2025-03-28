package dk.lima.common.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {
    private final UUID ID = UUID.randomUUID();
    private final Map<Class<? extends IEntityComponent>, IEntityComponent> entityComponentMap;

    public Entity() {
        entityComponentMap = new ConcurrentHashMap<>();
    }

    public String getID() {
        return ID.toString();
    }

    public <E extends IEntityComponent> E getComponent(Class componentClass) {
        return (E) entityComponentMap.get(componentClass);
    }

    public void addComponent(IEntityComponent component) {
        entityComponentMap.put(component.getClass(), component);
    }

    public void removeComponent(Class<? extends IEntityComponent> componentClass) {
        entityComponentMap.remove(componentClass);
    }

    public void removeComponent(IEntityComponent component) {
        entityComponentMap.remove(component.getClass());
    }

    public Collection<IEntityComponent> getComponents() {
        return entityComponentMap.values();
    }
}
