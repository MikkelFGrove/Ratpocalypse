package dk.lima.pathfindingComponent;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.Entity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathfindingComponentTest {
    private static PathfindingComponent component;
    private static Entity entity;

    @BeforeAll
    static void setup() {
        entity = new Entity();
        component = new PathfindingComponent(entity);
    }

    @Test
    void testCalculatePathFindGoal() {
        Coordinate start = new Coordinate(0, 0);
        Coordinate goal = new Coordinate(100, 100);
        Coordinate[] path = component.calculatePath(start, goal);

        assertTrue(goal.equals(path[path.length - 1]));
    }
}