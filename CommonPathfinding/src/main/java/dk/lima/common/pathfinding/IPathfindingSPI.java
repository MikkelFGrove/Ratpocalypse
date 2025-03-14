package dk.lima.common.pathfinding;

import dk.lima.common.data.Coordinate;

public interface IPathfindingSPI {
    public Coordinate calculateNextStep(Coordinate start, Coordinate goal);
}
