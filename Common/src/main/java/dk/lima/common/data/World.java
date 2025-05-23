package dk.lima.common.data;

import dk.lima.common.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
    private Coordinate playerPosition = new Coordinate(1200,1200);
    private int[][] tileMap = new int[0][0];

    synchronized public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }

    public List<Entity> getEntities(EEntityTypes entityType) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            if (e.getEntityType().equals(entityType)) {
                r.add(e);
            }
        }
        return r;
    }

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

    public Coordinate getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Coordinate playerPosition) {
        this.playerPosition = playerPosition;
    }

    public int[][] getTileMap() {
        return tileMap;
    }

    public void setTileMap(int[][] tileMap) {
        this.tileMap = tileMap;
    }

    public boolean isCoordinateTraversable(Coordinate coordinate) {
        if (tileMap == null) return true;

        int x = (int) Math.floor((coordinate.getX())  / 48);
        int y = (int) Math.floor((coordinate.getY()) / 48);

        if ((x < 0 || y < 0) || (x >= tileMap.length || y >= tileMap[x].length)) return true;

        return !(tileMap[x][y] == 18 | tileMap[x][y] == 2);
    }
}
