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
    private final int maxWorldCol = 50;
    private final int maxWorldRow = 50;

    private Entity createObstacle(GameData gameData, Coordinate coordinate) {
        Entity obstacle = new Obstacle();

        String[] pathsToSprites = {"barrel.png"};

        for (IEntityComponent component : getEntityComponents()) {
            switch (component.getType()) {
                case SPRITE -> {
                    SpriteCP spriteCP = (SpriteCP) component;
                    spriteCP.setAmountOfSprites(pathsToSprites.length);
                    spriteCP.setPathsToSprite(pathsToSprites);
                    spriteCP.setHeight(gameData.tileSize);
                    spriteCP.setWidth(gameData.tileSize);
                    obstacle.addComponent(spriteCP);
                }
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

        String[] pathsToSprites = {"lava.png"};

        for (IEntityComponent component : getEntityComponents()) {
            switch (component.getType()) {
                case SPRITE -> {
                    SpriteCP spriteCP = (SpriteCP) component;
                    spriteCP.setAmountOfSprites(pathsToSprites.length);
                    spriteCP.setPathsToSprite(pathsToSprites);
                    spriteCP.setHeight(gameData.tileSize);
                    spriteCP.setWidth(gameData.tileSize);
                    hazard.addComponent(spriteCP);
                }
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
        int[][] tileMap = loadMap("/WorldGenerator/Maps/worldMap01.txt");
        world.setTileMap(tileMap);

        for (int col = 0; col < tileMap.length; col++) {
            for (int row = 0; row < tileMap[col].length; row++) {
                Entity entity = switch (tileMap[col][row]) {
                    case 12 -> createObstacle(gameData, new Coordinate(col * gameData.tileSize + (gameData.tileSize / 2d), row * gameData.tileSize + (gameData.tileSize / 2d)));
                    case 14 -> createHazard(gameData, new Coordinate(col * gameData.tileSize + (gameData.tileSize / 2d), row * gameData.tileSize + (gameData.tileSize / 2d)));
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

    public static Collection<? extends IEntityComponent> getEntityComponents() {
        return ServiceLoader.load(IEntityComponent.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    public int[][] loadMap(String map) {
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
