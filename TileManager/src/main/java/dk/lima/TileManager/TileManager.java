package dk.lima.TileManager;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;

import dk.lima.common.graphics.IGraphicsService;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager implements IGraphicsService {

    // Screen Settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // This is the actual size of the tile displayed
    public final int maxScreenCol = 16; // screen length in tiles
    public final int maxScreenRow = 12; //  Screen height in tiles

    // World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // Needed for the tile management
    private Tile[] tiles;
    private int[][] mapTileNum;
    private Canvas canvas;
    private GraphicsContext gc;


    public void getFileImage() {
        tiles[0] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath03.png")));
        tiles[1] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath05.png")));
        tiles[2] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath08.png")));
        tiles[3] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath02.png")));
        tiles[4] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath09.png")));
        tiles[5] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath06.png")));
        tiles[6] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath07.png")));
        tiles[7] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath11.png")));
        tiles[8] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath10.png")));
        tiles[9] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/pathNoBorder.png")));
        tiles[10] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/path.png")));
        tiles[11] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath04.png")));
        tiles[12] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/wall.png")));
        tiles[13] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/grass.png")));
        tiles[14] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/toxic_water.png")));
        tiles[15] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/tree01.png")));
        tiles[16] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/flowerField01.png")));
    }

    public void loadMap(String map) {
        try(InputStream inputStream = getClass().getResourceAsStream(map);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            int col = 0;
            int row = 0;

            while (col < maxWorldCol && row < maxWorldRow) {
                String line = bufferedReader.readLine();
                while (col < maxWorldCol) {
                    String[] numbers = line.split(",");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num-1;
                    col++;
                }

                if(col == maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Map not found");
        }

    }

    @Override
    public Node createComponent(GameData gameData, World world) {
        tiles = new Tile[30];
        mapTileNum = new int[maxWorldCol][maxWorldRow];

        canvas = new Canvas(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        getFileImage();
        loadMap("/TileManager/Maps/worldMap01.txt");

        return canvas;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int[] startTile = calculateStartCoord(gameData, world);
        int startCol = startTile[0];
        int worldCol = startCol;
        int worldRow = startTile[1];

        int[] endTile = calculateEndCoord(gameData, world);
        int endWorldCol = endTile[0];
        int endWorldRow = endTile[1];

        double playerCoordinateX = gameData.getDisplayWidth() / 2d - world.getPlayerPosition().getX();
        double playerCoordinateY = gameData.getDisplayHeight() / 2d - world.getPlayerPosition().getY();

        while(worldCol < endWorldCol && worldRow < endWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            double x = worldCol * tileSize + playerCoordinateX;
            double y = worldRow * tileSize + playerCoordinateY;
            if(tileNum >= 0 && tileNum <= tiles.length && tiles[tileNum] != null) {
                gc.drawImage(tiles[tileNum].img, x, y, tileSize, tileSize);
            }
            worldCol++;

            if(worldCol == endWorldCol) {
                worldCol = startCol;
                worldRow++;
            }
        }
    }

    @Override
    public void showComponent(Boolean shouldShow) {
        canvas.setVisible(shouldShow);
    }

    private int[] calculateStartCoord(GameData gameData, World world) {
        // Calculate the position of the tile in the top-left corner e.g. the first tile.
        int worldCol = Math.floorDiv((int) (world.getPlayerPosition().getX() - gameData.getDisplayWidth() / 2d), tileSize);
        int worldRow = Math.floorDiv((int) (world.getPlayerPosition().getY() - gameData.getDisplayHeight() / 2d), tileSize);

        // If the start-tile is out of bounds, then just set the row/col to 0.
        if (worldCol < 0) {
            worldCol = 0;
        }
        if (worldRow < 0) {
            worldRow = 0;
        }
        return new int[]{worldCol, worldRow};
    }

    private int[] calculateEndCoord(GameData gameData, World world) {
        // Calculate the position of the tile in the top-left corner e.g. the first tile.
        int worldCol = Math.ceilDiv((int) (world.getPlayerPosition().getX() + gameData.getDisplayWidth() / 2d), tileSize);
        int worldRow = Math.ceilDiv((int) (world.getPlayerPosition().getY() + gameData.getDisplayHeight() / 2d), tileSize);

        // If the start-tile is out of bounds, then just set the row/col to 0.
        if (worldCol > maxWorldCol) {
            worldCol = maxWorldCol;
        }
        if (worldRow > maxWorldRow) {
            worldRow = maxWorldRow;
        }
        return new int[]{worldCol, worldRow};
    }
}

