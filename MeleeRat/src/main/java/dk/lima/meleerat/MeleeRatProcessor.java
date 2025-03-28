package dk.lima.meleerat;

import dk.lima.common.data.*;
import dk.lima.common.services.IEntityProcessingService;


public class MeleeRatProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(MeleeRat.class)) {
            /*Coordinate start = new Coordinate(enemy.getX(), enemy.getY());
            Coordinate nextStep = new Coordinate(enemy.getX(), enemy.getY());


            enemy.setX(nextStep.getX());
            enemy.setY(nextStep.getY());*/

            /*double ratio = (nextStep.getY() - start.getY()) / (nextStep.getX() - start.getX());
            double angle = Math.toDegrees(Math.atan(ratio));

            // If difference is negative, add 180 to angle, to get correct angle
            if (nextStep.getX() - start.getX() < 0) {
                angle = 180 + angle;
            }

            enemy.setRotation(angle);*/

            for (IEntityComponent component : enemy.getComponents()) {
                component.process(gameData, world);
            }
        }
    }
}
