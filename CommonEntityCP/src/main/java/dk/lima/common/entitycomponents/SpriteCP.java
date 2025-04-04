package dk.lima.common.entitycomponents;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.IEntityComponent;

import javafx.scene.image.Image;

import java.util.Objects;

public class SpriteCP implements IEntityComponent {
    public Image image;

    private int width, height;

    private String[] pathsToSprite;

    public SpriteCP(String[] spritePaths, int amountOfSprites, int scale) {
        setAmountOfSprites(amountOfSprites);
        for(int i = 0; i < pathsToSprite.length; i++) {
            setPathsToSprite(spritePaths[i], i);
        }
        setSprite(pathsToSprite[0]);
    }

    public void setAmountOfSprites(int amount) {
      pathsToSprite = new String[amount];
    }

    public void setPathsToSprite(String path, int placement) {
        pathsToSprite[placement] = path;
    }


    public Image getImage() {
        return image;
    }

    public void setSprite(String pathToSprite) {
        try {
            image =new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(pathToSprite)));

        } catch (NullPointerException e) {
            System.out.println("Image not found");
        }

    }




    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    
    @Override
    public void process(GameData gameData, World world) {

    }
}
