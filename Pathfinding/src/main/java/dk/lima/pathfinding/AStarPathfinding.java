package dk.lima.pathfinding;

import dk.lima.common.data.Coordinate;
import dk.lima.common.pathfinding.IPathfindingSPI;

import java.util.*;

public class AStarPathfinding implements IPathfindingSPI {
    double scalingFactor = 1;
    @Override
    public Coordinate calculateNextStep(Coordinate start, Coordinate goal) {
        /*Coordinate result = new Coordinate(start.getX(), start.getY());
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

        return result;*/
        Coordinate[] coordinates = calculateNextSteps(start, goal);
        return coordinates[coordinates.length - 1];
    }

    @Override
    public Coordinate[] calculateNextSteps(Coordinate start, Coordinate goal) {
        List<Node> fringe = new ArrayList<>();
        Node initialNode = new Node(start);
        fringe.add(initialNode);
        while (!fringe.isEmpty()) {
            Node currentNode = fringe.getFirst();
            fringe.removeFirst();
            System.out.println(fringe.size());
            if (currentNode.getCoordinates().equals(goal)) {
                // Return the next steps coordinates.
                Coordinate[] coordinates = new Coordinate[currentNode.getPath().size()];
                for (int i = 0; i < coordinates.length; i++) {
                    coordinates[i] = currentNode.getPath().get(i).getCoordinates();
                }
                return coordinates;
            }
            Node[] children = expandNode(currentNode);
            for (Node child : children) {
                child.setHeuristicCost(heuristic(child.getCoordinates(), goal));
                fringe.add(child);
            }
            fringe.sort(null);
            if (fringe.size() > 5) {
                fringe.subList(5, fringe.size()).clear();
            }
        }

        return new Coordinate[]{start};
    }

    private double heuristic(Coordinate start, Coordinate goal) {
        //returns the straight line distance between start and goal
        return Math.sqrt(Math.pow((goal.getX() - start.getX()), 2) + Math.pow((goal.getY() - start.getY()), 2));
    }

    private Node[] expandNode(Node parentState){
        Node[] successorStates = new Node[4];
        successorStates[0] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()-1* scalingFactor, parentState.getCoordinates().getY()), 1);
        successorStates[1] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX() + 1*scalingFactor, parentState.getCoordinates().getY()), 1);
        successorStates[2] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX(), parentState.getCoordinates().getY()+1*scalingFactor), 1);
        successorStates[3] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX(), parentState.getCoordinates().getY()-1*scalingFactor), 1);
        //successorStates[4] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX() +1*scalingFactor, parentState.getCoordinates().getY()+1*scalingFactor), Math.sqrt(2));
        //successorStates[5] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()+1*scalingFactor, parentState.getCoordinates().getY()-1*scalingFactor), Math.sqrt(2));
        //successorStates[6] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()-1*scalingFactor,parentState.getCoordinates().getY()+1*scalingFactor), Math.sqrt(2));
        //successorStates[7] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()-1*scalingFactor, parentState.getCoordinates().getY()-1*scalingFactor), Math.sqrt(2));
        return successorStates;
    }
}