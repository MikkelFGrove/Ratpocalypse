package dk.lima.pathfinding;

import dk.lima.common.data.Coordinate;

public class Node {
    private Node parentNode;
    private Coordinate coordinates;
    private double totalCost;

    public Node(Node parentNode, Coordinate coordinates, double cost) {
        this.parentNode = parentNode;
        this.coordinates = coordinates;
        this.totalCost = parentNode.totalCost +cost;
    }

    public Node(Coordinate coordinates) {
        this.coordinates = coordinates;
        this.totalCost = 0;
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate coordinates) {
        this.coordinates = coordinates;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
