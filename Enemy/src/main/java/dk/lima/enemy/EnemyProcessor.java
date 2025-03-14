package dk.lima.enemy;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.enemy.Enemy;
import dk.lima.common.pathfinding.IPathfindingSPI;
import dk.lima.common.services.IEntityProcessingService;
import dk.lima.common.weapon.IWeaponSPI;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyProcessor implements IEntityProcessingService {
    private Entity player;

    @Override
    public void process(GameData gameData, World world) {
        if (player == null) {
            for (Entity p : world.getEntities(Player.class)) {
                player = p;
            }
        }

        for (Entity enemy : world.getEntities(Enemy.class)) {
            Coordinate start = new Coordinate(enemy.getX(), enemy.getY());
            Coordinate nextStep = new Coordinate(enemy.getX(), enemy.getY());
            if (getPathfindingSPI().stream().findFirst().isPresent() && world.getPlayerPosition() != null) {
                nextStep = getPathfindingSPI().stream().findFirst().get().calculateNextStep(start, world.getPlayerPosition());
            }

            enemy.setX(nextStep.getX());
            enemy.setY(nextStep.getY());
        }
    }

    private Collection<? extends IPathfindingSPI> getPathfindingSPI() {
        return ServiceLoader.load(IPathfindingSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
