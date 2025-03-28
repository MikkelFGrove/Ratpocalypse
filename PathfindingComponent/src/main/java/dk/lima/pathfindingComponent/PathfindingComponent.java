package dk.lima.pathfindingComponent;

import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.data.*;

import java.util.*;

public class PathfindingComponent implements IEntityComponent {
    private int stepsTaken;
    private Coordinate[] path;
    private Entity entity;
    private double scalingFactor = 0.5;
    // Value specifying how long the player has to move from the calculated path to calculate a new path
    private double goalRadius = 40;

    public PathfindingComponent() {
    }

    public PathfindingComponent(Entity entity) {
        this.entity = entity;
    }

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.PATHFINDING;
    }

    public void process(GameData gameData, World world) {
        if (world.getPlayerPosition() == null){
            return;
        }

        TransformCP transformCP = entity.getComponent(TransformCP.class);
        Coordinate coord = transformCP.getCoord();
        Coordinate nextStep = transformCP.getCoord().clone();

        // If enemy is within the radius, use straight-line pathfinding
        if (heuristic(new Coordinate(coord.getX(), coord.getY()), world.getPlayerPosition()) <= goalRadius) {
            Coordinate step = calculateStraightlineStep(coord, world.getPlayerPosition());
            nextStep.setX(step.getX());
            nextStep.setY(step.getY());
        } else {
            // Use A* for pathfinding if enemy is outside radius
            // Calculate new path if not existing, or if player has moved outside radius
            if (path == null || heuristic(path[path.length - 1], world.getPlayerPosition()) > goalRadius) {
                path = calculatePath(coord, world.getPlayerPosition());
                stepsTaken = 0;
            }

            if (path.length > stepsTaken) {
                nextStep = path[stepsTaken];
                // Increase amount of steps taken in the current A* path.
                stepsTaken++;
            }
        }

        // We use the coordinates of the player to angle the enemy
        double yDiff = world.getPlayerPosition().getY() - coord.getY();
        double xDiff = world.getPlayerPosition().getX() - coord.getX();
        double angle = Math.toDegrees(Math.atan2(yDiff, xDiff));

        transformCP.setCoord(nextStep);
        transformCP.setRotation(angle);
    }

    private Coordinate calculateStraightlineStep(Coordinate start, Coordinate goal) {
        Coordinate result = new Coordinate(start.getX(), start.getY());
        int yDiff = (int) (goal.getY() - start.getY());
        int xDiff = (int) (goal.getX() - start.getX());

        TransformCP transformCP = entity.getComponent(TransformCP.class);
        Coordinate coord = transformCP.getCoord();

        if (yDiff > 0) {
            result.setY(coord.getY() + scalingFactor);
        } else if (yDiff < 0) {
            result.setY(coord.getY() - scalingFactor);
        }

        if (xDiff > 0) {
            result.setX(coord.getX() + scalingFactor);
        } else if (xDiff < 0) {
            result.setX(coord.getX() - scalingFactor);
        }
        return result;
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

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
