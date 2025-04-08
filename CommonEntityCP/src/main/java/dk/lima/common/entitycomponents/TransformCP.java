package dk.lima.common.entitycomponents;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;

public class TransformCP implements IEntityComponent {
    private Coordinate coord;
    private double rotation;
    private double size;
    private Entity entity;

    public TransformCP() {
    }

    public TransformCP(Coordinate coord, double rotation, double size) {
        this.coord = coord;
        this.rotation = rotation;
        this.size = size;
    }

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.TRANSFORM;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void process(GameData gameData, World world) {

    }

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
