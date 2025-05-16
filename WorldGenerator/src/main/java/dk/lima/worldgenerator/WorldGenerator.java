package dk.lima.worldgenerator;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.IEntityComponent;
import dk.lima.common.entitycomponents.DamageCP;
import dk.lima.common.entitycomponents.HealthCP;
import dk.lima.common.entitycomponents.SpriteCP;
import dk.lima.common.entitycomponents.TransformCP;
import dk.lima.common.services.IGamePluginService;
import dk.lima.obstacle.Hazard;
import dk.lima.obstacle.Obstacle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class WorldGenerator implements IGamePluginService {
    // World settings
    private final int maxWorldCol = 75;
    private final int maxWorldRow = 75;

    private Entity createObstacle(GameData gameData, Coordinate coordinate) {
        Entity obstacle = new Obstacle();

        for (IEntityComponent component : getEntityComponents()) {
            switch (component.getType()) {
                case TRANSFORM -> {
                    TransformCP transformCP = (TransformCP) component;
                    transformCP.setCoord(coordinate);
                    transformCP.setSize(gameData.tileSize / 2d);
                    obstacle.addComponent(transformCP);
                }
            }
        }

        return obstacle;
    }

    private Entity createHazard(GameData gameData, Coordinate coordinate) {
        Entity hazard = new Hazard();

        for (IEntityComponent component : getEntityComponents()) {
            switch (component.getType()) {
                case TRANSFORM -> {
                    TransformCP transformCP = (TransformCP) component;
                    transformCP.setCoord(coordinate);
                    transformCP.setSize(gameData.tileSize / 2d);
                    hazard.addComponent(transformCP);
                }
                case DAMAGE -> {
                    DamageCP damageCP = (DamageCP) component;
                    damageCP.setAttackDamage(0.1);
                    hazard.addComponent(damageCP);
                }
            }
        }

        return hazard;
    }

    @Override
    public void start(GameData gameData, World world) {
        int[][] tileMap = loadMap("/WorldGenerator/Maps/TheFinalMap.txt");
        world.setTileMap(tileMap);

        for (int col = 0; col < tileMap.length; col++) {
            for (int row = 0; row < tileMap[col].length; row++) {
                Entity entity = switch (tileMap[col][row]) {
                    case 2 -> createObstacle(gameData, new Coordinate(col * gameData.tileSize + (gameData.tileSize / 2d), row * gameData.tileSize + (gameData.tileSize / 2d)));
                    case 16 -> createHazard(gameData, new Coordinate(col * gameData.tileSize + (gameData.tileSize / 2d), row * gameData.tileSize + (gameData.tileSize / 2d)));
                    default -> null;
                };

                if (entity != null) {
                    world.addEntity(entity);
                }
            }
        }
    }

    @Override
    public void stop(GameData gameData, World world) {

    }

    private static Collection<? extends IEntityComponent> getEntityComponents() {
        return ServiceLoader.load(IEntityComponent.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private int[][] loadMap(String map) {
        int[][] tileMap = new int[maxWorldCol][maxWorldRow];

        try (InputStream inputStream = getClass().getResourceAsStream(map);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            int col = 0;
            int row = 0;

            while (col < maxWorldCol && row < maxWorldRow) {
                String line = bufferedReader.readLine();
                while (col < maxWorldCol) {
                    String[] numbers = line.split(",");

                    int num = Integer.parseInt(numbers[col]);

                    tileMap[col][row] = num-1;
                    col++;
                }

                if(col == maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            System.out.println("Map not found");
        }

        return tileMap;
    }

}
