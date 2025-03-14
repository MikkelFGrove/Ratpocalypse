package dk.lima.pathfinding;

import dk.lima.common.data.Coordinate;
import dk.lima.common.pathfinding.IPathfindingSPI;

import java.util.Map;

public class AStarPathfinding implements IPathfindingSPI {
    double scalingFactor = 0.2;
    @Override
    public Coordinate calculateNextStep(Coordinate start, Coordinate goal) {
        Coordinate result = new Coordinate(start.getX(), start.getY());
        int yDiff = (int) (goal.getY() - start.getY());
        int xDiff = (int) (goal.getX() - start.getX());

        if (yDiff > 0) {
            result.setY(result.getY() + scalingFactor);
        } else if (yDiff < 0) {
            result.setY(result.getY() - scalingFactor);
        }

        if (xDiff > 0) {
            result.setX(result.getX() + scalingFactor);
        } else if (xDiff < 0) {
            result.setX(result.getX() - scalingFactor);
        }

        return result;
    }

    private double calculateCost(double startX, double startY, double goalX, double goalY) {
        return heuristic(startX, startY, goalX, goalY);
    }

    private double heuristic(double startX, double startY, double goalX, double goalY) {
        //returns the straight line distance between start and goal
        return Math.sqrt(Math.pow((goalX - startX), 2) + Math.pow((goalY - startY), 2));
    }

    private Node[] expandNode(Node parentState){
        Node[] successorStates = new Node[9];
        successorStates[0] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX(), parentState.getCoordinates().getY()), 1);
        successorStates[1] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()-1* scalingFactor, parentState.getCoordinates().getY()), 1);
        successorStates[2] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX() + 1*scalingFactor, parentState.getCoordinates().getY()), 1);
        successorStates[3] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX(), parentState.getCoordinates().getY()+1*scalingFactor), 1);
        successorStates[4] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX(), parentState.getCoordinates().getY()-1*scalingFactor), 1);
        successorStates[5] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX() +1*scalingFactor, parentState.getCoordinates().getY()+1*scalingFactor), 1);
        successorStates[6] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()+1*scalingFactor, parentState.getCoordinates().getY()-1*scalingFactor), 1);
        successorStates[7] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()-1*scalingFactor,parentState.getCoordinates().getY()+1*scalingFactor), 1);
        successorStates[8] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()-1*scalingFactor, parentState.getCoordinates().getY()-1*scalingFactor), 1);
        return successorStates;
    }
}

class Node {
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
