package dk.lima.pathfinding;

import dk.lima.common.data.Coordinate;
import dk.lima.common.pathfinding.IPathfindingSPI;

public class AStarPathfinding implements IPathfindingSPI {
    @Override
    public Coordinate calculateNextStep(Coordinate start, Coordinate goal) {
        Coordinate result = new Coordinate(start.getX(), start.getY());
        double scalingFactor = 0.2;
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
        return Math.sqrt(Math.pow((goalX - startX), 2) + Math.pow((goalY - startY), 2));
    }
}
