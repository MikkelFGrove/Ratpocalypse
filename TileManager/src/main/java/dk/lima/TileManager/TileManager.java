package dk.lima.TileManager;

import dk.lima.common.data.GameData;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

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
    private final Pane game;
    private final GameData gameData;
    private final Tile[] tiles;
    private final int[][] mapTileNum;
    private final Canvas canvas;
    private final GraphicsContext gc;


    public TileManager(Pane pane) {
        this.game = pane;
        gameData = new GameData();
        tiles = new Tile[30];
        mapTileNum = new int[maxWorldCol][maxWorldRow];

        canvas = new Canvas(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gc = canvas.getGraphicsContext2D();
        game.getChildren().add(canvas);

        getFileImage();
        loadMap("/TileManager/Maps/worldMap01.txt");

    }

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
        tiles[14] = new Tile(new Image(getClass().getResourceAsStream("/TileManager/Tiles/water.png")));
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

    public void draw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(int col = 0;  col < maxWorldCol; col++) {
            for(int row = 0; row < maxWorldRow; row++) {
                int tileNum = mapTileNum[col][row];
                double x = col * tileSize;
                double y = row * tileSize;
                if(tileNum >= 0 && tileNum <= tiles.length && tiles[tileNum] != null) {
                    gc.drawImage(tiles[tileNum].img, x, y, tileSize, tileSize);
                }
            }
        }
    }
}

