package dk.lima.common.entitycomponents;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;

public class ShapeCP implements IEntityComponent {
    private double[] polygonCoordinates;
    private int[] color;
    private Entity entity;

    public ShapeCP() {
    }

    // Nice comment :-) Plz merge
    public ShapeCP(double[] polygonCoordinates, int[] color) {
        this.polygonCoordinates = polygonCoordinates;
        this.color = color;
    }

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.SHAPE;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void process(GameData gameData, World world) {
    }

    public double[] getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public void setPolygonCoordinates(double[] polygonCoordinates) {
        this.polygonCoordinates = polygonCoordinates;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }
}
