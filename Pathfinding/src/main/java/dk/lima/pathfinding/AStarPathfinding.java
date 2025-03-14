package dk.lima.pathfinding;

import dk.lima.common.data.Coordinate;
import dk.lima.common.pathfinding.IPathfindingSPI;

public class AStarPathfinding implements IPathfindingSPI {
    @Override
    public Coordinate calculateNextStep(Coordinate start, Coordinate goal) {
        Coordinate result = new Coordinate(start.getX(), start.getY());
        if (goal.getY() > start.getY()) {
            result.setY(result.getY() + 1);
        } else if (goal.getY() < start.getY()) {
            result.setY(result.getY() - 1);
        }

        if (goal.getX() > start.getX()) {
            result.setX(result.getX() + 1);
        } else if (goal.getX() < start.getX()) {
            result.setX(result.getX() - 1);
        }

        return result;
    }

    private double calculateCost(double startX, double startY, double goalX, double goalY) {
        return heuristic(startX, startY, goalX, goalY);
    }

    private double heuristic(double startX, double startY, double goalX, double goalY) {
        return Math.sqrt(Math.pow((goalX - startX), 2) + Math.pow((goalY - startY), 2));
    }
}
