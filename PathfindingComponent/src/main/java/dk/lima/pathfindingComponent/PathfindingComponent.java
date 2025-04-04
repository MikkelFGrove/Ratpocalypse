package dk.lima.pathfindingComponent;

import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.entitycomponents.TransformCP;

import java.util.*;

public class PathfindingComponent implements IEntityComponent {
    private int stepsTaken;
    private Coordinate[] path;
    private Entity entity;
    private double maxScalingFactor = 24;
    // Value specifying how long the player has to move from the calculated path to calculate a new path
    private double goalRadius = 0.65;

    public PathfindingComponent() {
    }

    public PathfindingComponent(Entity entity) {
        this.entity = entity;
    }

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.PATHFINDING;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void process(GameData gameData, World world) {
        if (world.getPlayerPosition() == null){
            return;
        }

        TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);
        Coordinate coord = transformCP.getCoord();
        Coordinate nextStep = transformCP.getCoord().clone();

        // Calculate new path if path is empty or if player has moved a certain distance from current goal
        if (path == null || heuristic(path[path.length - 1], world.getPlayerPosition()) > goalRadius * heuristic(coord, world.getPlayerPosition())) {
            path = calculatePath(coord, world.getPlayerPosition());
            stepsTaken = 0;
        }

        if (path.length > stepsTaken) {
            nextStep = path[stepsTaken];
        }

        Coordinate calculatedStep = calculateStraightLineStep(coord, nextStep, 1);

        // We use the coordinates of the player to angle the enemy
        double yDiff = world.getPlayerPosition().getY() - coord.getY();
        double xDiff = world.getPlayerPosition().getX() - coord.getX();
        //double yDiff = calculatedStep.getY() - coord.getY();
        //double xDiff = calculatedStep.getX() - coord.getX();
        double angle = Math.toDegrees(Math.atan2(yDiff, xDiff));

        if (calculatedStep.equals(nextStep)) {
            stepsTaken++;
        }

        transformCP.setCoord(calculatedStep);
        transformCP.setRotation(angle);
    }

    private Coordinate calculateStraightLineStep(Coordinate start, Coordinate goal, double length) {
        if (start.equals(goal)) {
            return start;
        }

        double xDistance = goal.getX() - start.getX();
        double yDistance = goal.getY() - start.getY();

        double hyp = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
        double scaledHyp = hyp / length;

        return new Coordinate(start.getX() + xDistance / scaledHyp, start.getY() + yDistance / scaledHyp);
    }

    public Coordinate[] calculatePath(Coordinate start, Coordinate goal) {
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

            Node[] children;

            double dist = heuristic(goal, currentNode.getCoordinates());
            if (dist >= maxScalingFactor) {
                children = expandNode(currentNode, maxScalingFactor);
            } else {
                children = expandNode(currentNode, dist);
            }

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

    private Node[] expandNode(Node parentState, double scalingFactor){
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
