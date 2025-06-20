package dk.lima.pathfindingComponent;

import dk.lima.common.data.Coordinate;
import dk.lima.common.entity.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.pathfinding.IPathfinding;

import java.util.*;

public class PathfindingComponent implements IEntityComponent, IPathfinding {
    private int stepsTaken;
    private Coordinate[] path;
    private Entity entity;
    private double maxStepSize = 48;
    private int[][] map;
    // Value specifying how long the player has to move from the calculated path to calculate a new path
    private double goalRadius = 0.65;
    private Coordinate target;
    private double length = 1.75;

    public PathfindingComponent() {
    }

    public PathfindingComponent(Entity entity) {
        this.entity = entity;
    }

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.PATHFINDING;
    }

    public Coordinate[] getPath() {
        return path;
    }

    public int getStepsTaken() {
        return stepsTaken;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void setTarget(Coordinate target) {
        this.target = target;
    }

    @Override
    public void setLength(double length) {
        this.length = length;
    }

    public void process(GameData gameData, World world) {
        if (target == null){
            return;
        }

        map = world.getTileMap();
        maxStepSize = gameData.getTileSize();

        TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);
        Coordinate coord = transformCP.getCoord();
        Coordinate nextStep = transformCP.getCoord().clone();

        // Calculate new path if path is empty or if player has moved a certain distance from current goal
        if (path == null || (heuristic(path[path.length - 1], target) > goalRadius * heuristic(coord, target) && world.isCoordinateTraversable(target))) {
            path = calculatePath(coord, target, world);
            stepsTaken = 0;
        }

        if (path.length > stepsTaken) {
            nextStep = path[stepsTaken];
        }

        Coordinate calculatedStep = calculateStraightLineStep(coord, nextStep, length);

        // We use the coordinates of the player to angle the enemy
        double yDiff = target.getY() - coord.getY();
        double xDiff = target.getX() - coord.getX();
        //double yDiff = calculatedStep.getY() - coord.getY();
        //double xDiff = calculatedStep.getX() - coord.getX();
        double angle = Math.toDegrees(Math.atan2(yDiff, xDiff));

        if (calculatedStep.approxEquals(nextStep)) {
            stepsTaken++;
        }

        transformCP.setCoord(calculatedStep);
        transformCP.setRotation(angle);
    }

    private Coordinate calculateStraightLineStep(Coordinate start, Coordinate goal, double length) {
        if (start.approxEquals(goal)) {
            return start;
        }

        double xDistance = goal.getX() - start.getX();
        double yDistance = goal.getY() - start.getY();

        double hyp = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
        double scaledHyp = hyp / length;

        return new Coordinate(start.getX() + xDistance / scaledHyp, start.getY() + yDistance / scaledHyp);
    }

    public Coordinate[] calculatePath(Coordinate start, Coordinate goal, World world) {
        TreeSet<Node> fringe = new TreeSet<>();
        HashSet<Node> expandedNodes = new HashSet<>();
        Node initialNode = new Node(start);
        fringe.add(initialNode);

        while (!fringe.isEmpty()) {
            Node currentNode = fringe.removeFirst();
            if (currentNode.getCoordinates().approxEquals(goal)) {
                // Return the next steps coordinates.
                List<Node> path = currentNode.getPath(); // already reversed (start to goal)
                Coordinate[] coordinates = new Coordinate[path.size()];
                for (int i = 0; i < path.size(); i++) {
                    coordinates[i] = path.get(i).getCoordinates();
                }
                return coordinates;
            }

            List<Node> children;

            double dist = heuristic(goal, currentNode.getCoordinates());
            if (dist >= maxStepSize) {
                children = expandNode(currentNode, maxStepSize, world, expandedNodes);
            } else {
                children = expandNode(currentNode, dist, world, expandedNodes);
            }

            for (Node child : children) {
                child.setHeuristicCost(heuristic(child.getCoordinates(), goal));
                fringe.add(child);
            }
        }

        return new Coordinate[]{start};
    }

    private double heuristic(Coordinate start, Coordinate goal) {
        //returns the straight line distance between start and goal
        return Math.sqrt(Math.pow((goal.getX() - start.getX()), 2) + Math.pow((goal.getY() - start.getY()), 2));
    }

    private List<Node> expandNode(Node parentState, double scalingFactor, World world, Set<Node> expandedNodes){
        List<Node> successorStates = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Coordinate coordinate = new Coordinate(parentState.getCoordinates().getX() + i * scalingFactor, parentState.getCoordinates().getY() + j * scalingFactor);
                if ((i == j && i == 0) || !world.isCoordinateTraversable(coordinate)) {
                    continue;
                }
                Node successorState = new Node(parentState, coordinate, Math.sqrt(Math.pow(i * scalingFactor, 2) + Math.pow(j * scalingFactor, 2)));
                if (!successorState.equals(parentState) && !expandedNodes.contains(successorState)) {
                    successorStates.add(successorState);
                    expandedNodes.add(successorState);
                }
            }
        }
        return successorStates;
    }
}
