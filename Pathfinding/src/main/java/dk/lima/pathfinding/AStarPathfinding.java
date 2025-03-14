package dk.lima.pathfinding;

import dk.lima.common.data.Coordinate;
import dk.lima.common.pathfinding.IPathfindingSPI;

public class AStarPathfinding implements IPathfindingSPI {
    @Override
    public Coordinate calculateNextStep(Coordinate start, Coordinate goal) {

    }

    private double heuristic(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }
}
