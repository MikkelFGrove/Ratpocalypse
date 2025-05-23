package dk.lima.common.entitycomponents;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.EGameInputs;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;

public class MovementCP implements IEntityComponent {

    Entity entity;
    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.MOVEMENT;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void process(GameData gameData, World world) {
            TransformCP transformCP = (TransformCP) entity.getComponent(EntityComponentTypes.TRANSFORM);

            double x = gameData.getMousePosition().getX() - gameData.getDisplayWidth() / 2d;
            double y = gameData.getMousePosition().getY() - gameData.getDisplayHeight() / 2d;
            double angle = Math.atan2(y, x);
            transformCP.setRotation(Math.toDegrees(angle));

            Coordinate playerCoord = transformCP.getCoord();
            double velocity = 2.5;
            //Checks what input is registered and then either move, rotate or fires a bullet based on that.
            if (gameData.getInputs().isDown(EGameInputs.UP)) {
                //Updates the player's world position ensuring it can move, and keeps track on where the player is in the world.
                double playerX = playerCoord.getX();
                double playerY = playerCoord.getY() - velocity - transformCP.getSize() ;
                if (world.isCoordinateTraversable(new Coordinate(playerX, playerY))) {
                    playerCoord.setY(playerCoord.getY() - velocity);
                }
            }
            if (gameData.getInputs().isDown(EGameInputs.DOWN)) {
                //Updates the player's world position ensuring it can move, and keeps track on where the player is in the world
                double playerX = playerCoord.getX() ;
                double playerY = playerCoord.getY() + velocity + transformCP.getSize() ;
                if (world.isCoordinateTraversable(new Coordinate(playerX, playerY))) {
                    playerCoord.setY(playerCoord.getY() + velocity);
                }
            }
            if (gameData.getInputs().isDown(EGameInputs.LEFT)) {
                //Updates the player's world position ensuring it can move, and keeps track on where the player is in the world.
                double playerX = playerCoord.getX() - velocity - transformCP.getSize() ;
                double playerY = playerCoord.getY();
                if (world.isCoordinateTraversable(new Coordinate(playerX, playerY))) {
                    playerCoord.setX(playerCoord.getX() - velocity);
                }
            }
            if (gameData.getInputs().isDown(EGameInputs.RIGHT)) {
                //Updates the player's world position ensuring it can move, and keeps track on where the player is in the world.
                double playerX = playerCoord.getX() + velocity + transformCP.getSize() ;
                double playerY = playerCoord.getY();
                if (world.isCoordinateTraversable(new Coordinate(playerX, playerY))) {
                    playerCoord.setX(playerCoord.getX() + velocity);
                }
            }
            world.setPlayerPosition(playerCoord);
    }
}
