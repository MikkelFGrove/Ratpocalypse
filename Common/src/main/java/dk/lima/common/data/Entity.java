package dk.lima.common.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();
    
    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;
    private float radius;
    private int[] color = new int[]{0, 0, 0};
    private ArrayList<IEntityComponent> components = new ArrayList<>();
            

    public String getID() {
        return ID.toString();
    }


    public void setPolygonCoordinates(double... coordinates ) {
        this.polygonCoordinates = coordinates;
    }

    public double[] getPolygonCoordinates() {
        return polygonCoordinates;
    }
       

    public void setX(double x) {
        this.x =x;
    }

    public double getX() {
        return x;
    }

    
    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
        
    public float getRadius() {
        return this.radius;
    }

    public void setColor(int[] color) {
        if (color.length != 3) this.color = new int[]{0, 0, 0};
        this.color = color;
    }

    public int[] getColor() {
        return this.color;
    }

    public ArrayList<IEntityComponent> getComponents() {
        return components;
    }

    public void addComponent(IEntityComponent component) {
        this.components.add(component);
    }
}
