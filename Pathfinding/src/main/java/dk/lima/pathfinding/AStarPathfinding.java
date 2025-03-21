package dk.lima.pathfinding;

import dk.lima.common.data.Coordinate;
import dk.lima.common.pathfinding.IPathfindingSPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        List<Node> fringe = new ArrayList<>();
        Node initialNode = new Node(start);
        fringe.add(initialNode);
        while (!fringe.isEmpty()) {
            Node currentNode = fringe.getFirst();
            fringe.removeFirst();
            System.out.println("Goal: " + goal.getX() + " " + goal.getY());
            System.out.println(currentNode.getCoordinates().getX() + " " + currentNode.getCoordinates().getY());
            if (currentNode.getCoordinates().equals(goal)) {
                // Return the next steps coordinates.
                return currentNode.getPath().getLast().getCoordinates();
            }
            List<Node> children = List.of(expandNode(currentNode));
            for (Node child : children) {
                child.setHeuristicCost(heuristic(child.getCoordinates(), goal));
            }
            fringe.addAll(children);
            fringe.sort(null);
            /*if (fringe.size() > 5) {
                fringe.subList(5, fringe.size()).clear();
            }*/
        }

        return start;
    }

    private double heuristic(Coordinate start, Coordinate goal) {
        //returns the straight line distance between start and goal
        return Math.sqrt(Math.pow((goal.getX() - start.getX()), 2) + Math.pow((goal.getY() - start.getY()), 2));
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