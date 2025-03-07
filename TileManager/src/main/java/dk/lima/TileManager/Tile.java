package dk.lima.TileManager;

import javafx.scene.image.Image;
public class Tile {

    public Image img;
    public boolean collision = false;

    public Tile(Image img) {
        this.img = img;
    }
}
