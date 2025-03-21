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

    private double calculateCost(Node state, double goalX, double goalY) {
        return state.getTotalCost() + heuristic(state.getCoordinates().getX(), state.getCoordinates().getY(), goalX, goalY);
    }

    private double heuristic(double startX, double startY, double goalX, double goalY) {
        //returns the straight line distance between start and goal
        return Math.sqrt(Math.pow((goalX - startX), 2) + Math.pow((goalY - startY), 2));
    }

    private Node[] expandNode(Node parentState){
        Node[] successorStates = new Node[9];
        successorStates[0] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX(), parentState.getCoordinates().getY()), 0);
        successorStates[1] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()-1* scalingFactor, parentState.getCoordinates().getY()), 1);
        successorStates[2] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX() + 1*scalingFactor, parentState.getCoordinates().getY()), 1);
        successorStates[3] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX(), parentState.getCoordinates().getY()+1*scalingFactor), 1);
        successorStates[4] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX(), parentState.getCoordinates().getY()-1*scalingFactor), 1);
        successorStates[5] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX() +1*scalingFactor, parentState.getCoordinates().getY()+1*scalingFactor), Math.sqrt(2));
        successorStates[6] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()+1*scalingFactor, parentState.getCoordinates().getY()-1*scalingFactor), Math.sqrt(2));
        successorStates[7] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()-1*scalingFactor,parentState.getCoordinates().getY()+1*scalingFactor), Math.sqrt(2));
        successorStates[8] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()-1*scalingFactor, parentState.getCoordinates().getY()-1*scalingFactor), Math.sqrt(2));
        return successorStates;
    }
}