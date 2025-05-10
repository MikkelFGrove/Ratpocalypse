package dk.lima.flockComponent;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.EEntityTypes;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.pathfinding.IPathfinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FlockCP implements IEntityComponent {
    private final int playerHighDistanceThreshold = 400;
    private final double playerLowHealthThreshold = 0.3;
    private final int minimumFlockSize = 3;
    private final double flockDistance = 125;
    private boolean hasAttacked = false;

    private Entity entity;

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.FLOCK;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void process(GameData gameData, World world) {
        if (hasAttacked) {
            attack(world);
            return;
        }

        List<Entity> flockEntities = new ArrayList<>();
        for (Entity e : world.getEntities()) {
            if (e.getComponent(EntityComponentTypes.FLOCK) != null) {
                flockEntities.add(e);
            }
        }
        flockEntities.remove(entity);

        if (isPopulationCritical(flockEntities) | isEntityInFlock(flockEntities)) {
            attack(world);
        }

        if (isPlayerHealthLow(world)) {
            if (isDistanceToPlayerHigh(world)) {
                flock(flockEntities);
            } else {
                attack(world);
            }
        } else {
            flock(flockEntities);
        }
    }

    private boolean isPopulationCritical(Collection<Entity> flock) {
        return flock.size() + 1 < minimumFlockSize;
    }

    private boolean isEntityInFlock(Collection<Entity> flock) {
        TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);
        Coordinate entityCoord = transformCP.getCoord();

        int flockSize = 1;
        for (Entity e : flock) {
            TransformCP transformCP2 = (TransformCP) e.getComponent(EntityComponentTypes.TRANSFORM);
            Coordinate entityCoord2 = transformCP2.getCoord();
            if (calculateDistance(entityCoord, entityCoord2) < flockDistance) {
                flockSize++;
            }
        }

        return flockSize >= minimumFlockSize;
    }

    private boolean isPlayerHealthLow(World world) {
        Entity player = world.getEntities(EEntityTypes.PLAYER).stream().findFirst().orElse(null);
        if (player != null) {
            HealthCP healthCP = (HealthCP) player.getComponent(EntityComponentTypes.HEALTH);
            if (healthCP != null) {
                return healthCP.getHealth() / healthCP.getMaxHealth() < playerLowHealthThreshold;
            }
        }

        return false;
    }

    private boolean isDistanceToPlayerHigh(World world) {
        TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);
        Coordinate entityCoord = transformCP.getCoord();
        Coordinate playerCoord = world.getPlayerPosition();

        return calculateDistance(entityCoord, playerCoord) > playerHighDistanceThreshold;
    }

    private void attack(World world) {
        hasAttacked = true;
        IEntityComponent component = entity.getComponent(EntityComponentTypes.PATHFINDING);
        if (component instanceof IPathfinding pathfinding) {
            pathfinding.setTarget(world.getPlayerPosition());
        }
    }

    private void flock(Collection<Entity> flock) {
        TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);
        Coordinate entityCoord = transformCP.getCoord();
        double nearestDistance = Double.MAX_VALUE;
        Coordinate nearestNeighbor = entityCoord;

        for (Entity e : flock) {
            TransformCP transformCP2 = (TransformCP) e.getComponent(EntityComponentTypes.TRANSFORM);
            Coordinate entityCoord2 = transformCP2.getCoord();
            double distance = calculateDistance(entityCoord, entityCoord2);

            if (distance > flockDistance &
                distance < nearestDistance) {
                nearestDistance = distance;
                nearestNeighbor = entityCoord2;
            }
        }

        IEntityComponent component = entity.getComponent(EntityComponentTypes.PATHFINDING);
        if (component instanceof IPathfinding pathfinding) {
            pathfinding.setTarget(nearestNeighbor);
        }
    }

    private double calculateDistance(Coordinate coord1, Coordinate coord2) {
        return Math.sqrt(
                Math.pow(coord1.getX() - coord2.getX(), 2) +
                Math.pow(coord1.getY() - coord2.getY(), 2));
    }
}
