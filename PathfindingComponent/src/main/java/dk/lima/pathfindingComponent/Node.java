package dk.lima.pathfindingComponent;

import dk.lima.common.data.Coordinate;

import java.util.ArrayList;

public class Node implements Comparable<Node>{
    private Node parentNode;
    private Coordinate coordinates;
    private double totalCost = 0;
    private double heuristicCost = 0;

    public Node(Node parentNode, Coordinate coordinates, double cost) {
        this.parentNode = parentNode;
        this.coordinates = coordinates;
        this.totalCost = parentNode.totalCost + cost;
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

    public double getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(double heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    // Returns path as a list of nodes starting at the end of the path.
    public ArrayList<Node> getPath() {
        ArrayList<Node> path = new ArrayList<>();
        Node currentNode = this;
        path.add(currentNode);
        while (currentNode.parentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.parentNode;
        }
        return path;
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(totalCost + heuristicCost, o.totalCost + o.getHeuristicCost());
    }
}
