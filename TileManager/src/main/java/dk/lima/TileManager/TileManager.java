package dk.lima.TileManager;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;

import dk.lima.common.entity.Entity;
import dk.lima.common.graphics.IGraphicsService;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TileManager implements IGraphicsService {
    // Needed for the tile management
    private Tile[] tiles;
    private int[][] mapTileNum;
    private int maxWorldCol;
    private int maxWorldRow;
    private Canvas canvas;
    private GraphicsContext gc;

    public void getFileImage() {
        tiles[0] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/flowerField01.png")));
        tiles[1] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/grass.png")));
        tiles[2] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/lava.png")));
        tiles[3] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/path.png")));
        tiles[4] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/pathNoBorder.png")));
        tiles[5] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath01.png")));
        tiles[6] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath02.png")));
        tiles[7] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath03.png")));
        tiles[8] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath04.png")));
        tiles[9] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath05.png")));
        tiles[10] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath06.png")));
        tiles[11] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath07.png")));
        tiles[12] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath08.png")));
        tiles[13] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath09.png")));
        tiles[14] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath10.png")));
        tiles[15] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/smallPath11.png")));
        tiles[16] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/toxic_water.png")));
        tiles[17] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/tree01.png")));
        tiles[18] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/wall.png")));
        tiles[19] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/water.png")));
    }

    @Override
    public Node createComponent(GameData gameData, World world) {
        tiles = new Tile[30];

        canvas = new Canvas(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        getFileImage();
        mapTileNum = world.getTileMap();
        maxWorldCol = mapTileNum.length;
        maxWorldRow = mapTileNum[0].length;

        return canvas;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int[] startTile = calculateStartCoord(gameData, world);
        int startCol = startTile[0];
        int worldCol = startCol, worldRow = startTile[1];

        int[] endTile = calculateEndCoord(gameData, world);
        int endWorldCol = endTile[0], endWorldRow = endTile[1];

        double playerCoordinateX = gameData.getDisplayWidth() / 2d - world.getPlayerPosition().getX();
        double playerCoordinateY = gameData.getDisplayHeight() / 2d - world.getPlayerPosition().getY();

        while(worldCol < endWorldCol && worldRow < endWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            double x = worldCol * gameData.getTileSize() + playerCoordinateX;
            double y = worldRow * gameData.getTileSize() + playerCoordinateY;
            if(tileNum >= 0 && tileNum <= tiles.length && tiles[tileNum] != null) {
                gc.drawImage(tiles[tileNum].img, x, y, gameData.getTileSize(), gameData.getTileSize());
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
        int worldCol = Math.floorDiv((int) (world.getPlayerPosition().getX() - gameData.getDisplayWidth() / 2d), gameData.getTileSize());
        int worldRow = Math.floorDiv((int) (world.getPlayerPosition().getY() - gameData.getDisplayHeight() / 2d), gameData.getTileSize());

        // If the end-tile is out of bounds, then clamp the values to avoid errors
        worldCol = Math.clamp(worldCol, 0, maxWorldCol);
        worldRow = Math.clamp(worldRow, 0, maxWorldRow);

        return new int[]{worldCol, worldRow};
    }

    private int[] calculateEndCoord(GameData gameData, World world) {
        // Calculate the position of the tile in the bottom-right corner e.g. the last tile.
        int worldCol = Math.ceilDiv((int) (world.getPlayerPosition().getX() + gameData.getDisplayWidth() / 2d), gameData.getTileSize());
        int worldRow = Math.ceilDiv((int) (world.getPlayerPosition().getY() + gameData.getDisplayHeight() / 2d), gameData.getTileSize());

        // If the end-tile is out of bounds, then clamp the values to avoid errors
        worldCol = Math.clamp(worldCol, 0, maxWorldCol);
        worldRow = Math.clamp(worldRow, 0, maxWorldRow);

        return new int[]{worldCol, worldRow};
    }
}

