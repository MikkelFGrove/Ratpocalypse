package dk.lima.pathfindingComponent;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.data.IEntityComponent;

import java.util.*;

public class PathfindingComponent implements IEntityComponent {
    private int stepsTaken;
    private Coordinate[] path;
    private Entity entity;
    private double scalingFactor = 0.8;

    public PathfindingComponent(Entity entity) {
        this.entity = entity;
    }

    public void process(GameData gameData, World world) {
        if (world.getPlayerPosition() == null){
            return;
        }
        if (path == null || (stepsTaken > 10 || path.length == 0)) {
            path = calculateNextSteps(new Coordinate(entity.getX(), entity.getY()), world.getPlayerPosition());
            stepsTaken = 0;
        }

        if (path.length > stepsTaken) {
            Coordinate nextStep = path[stepsTaken];

            double ratio = (nextStep.getY() - entity.getY()) / (nextStep.getX() - entity.getX());
            double angle = Math.toDegrees(Math.atan(ratio));

            // If difference is negative, add 180 to angle, to get correct angle
            if (nextStep.getX() - entity.getX() < 0) {
                angle = 180 + angle;
            }

            entity.setX(nextStep.getX());
            entity.setY(nextStep.getY());
            entity.setRotation(angle);
            stepsTaken++;
        }
    }

    public Coordinate[] calculateNextSteps(Coordinate start, Coordinate goal) {
        List<Node> fringe = new ArrayList<>();
        Set<Coordinate> visited = new HashSet<>();
        Node initialNode = new Node(start);
        fringe.add(initialNode);
        while (!fringe.isEmpty()) {
            Node currentNode = fringe.getFirst();
            fringe.removeFirst();
            if (visited.contains(currentNode.getCoordinates())) {
                continue; // Skip if already visited
            }
            visited.add(currentNode.getCoordinates());
            if (currentNode.getCoordinates().equals(goal)) {
                // Return the next steps coordinates.
                List<Node> path = currentNode.getPath(); // already reversed (start to goal)
                Coordinate[] coordinates = new Coordinate[path.size()];
                for (int i = 0; i < path.size(); i++) {
                    coordinates[i] = path.get(i).getCoordinates();
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
        Node[] successorStates = new Node[8];
        successorStates[0] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()-1* scalingFactor, parentState.getCoordinates().getY()), 1);
        successorStates[1] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX() + 1*scalingFactor, parentState.getCoordinates().getY()), 1);
        successorStates[2] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX(), parentState.getCoordinates().getY()+1*scalingFactor), 1);
        successorStates[3] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX(), parentState.getCoordinates().getY()-1*scalingFactor), 1);
        successorStates[4] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX() +1*scalingFactor, parentState.getCoordinates().getY()+1*scalingFactor), Math.sqrt(2));
        successorStates[5] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()+1*scalingFactor, parentState.getCoordinates().getY()-1*scalingFactor), Math.sqrt(2));
        successorStates[6] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()-1*scalingFactor,parentState.getCoordinates().getY()+1*scalingFactor), Math.sqrt(2));
        successorStates[7] = new Node(parentState, new Coordinate(parentState.getCoordinates().getX()-1*scalingFactor, parentState.getCoordinates().getY()-1*scalingFactor), Math.sqrt(2));
        return successorStates;
    }
}
