package dk.lima.common.entitycomponents;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.Entity;
import dk.lima.common.entity.EntityComponentTypes;
import dk.lima.common.entity.IEntityComponent;

import javafx.scene.image.Image;

import java.util.Objects;

public class SpriteCP implements IEntityComponent {
    private Image image;
    private int layer = 0;
    private int scale = 1;
    private int width, height;

    private String[] pathsToSprite;

    public SpriteCP() {
    }

    public SpriteCP(String[] spritePaths, int amountOfSprites, int scale) {
        setAmountOfSprites(amountOfSprites);
        for (int i = 0; i < pathsToSprite.length; i++) {
            setPathToSprite(spritePaths[i], i);
        }
        setSprite(pathsToSprite[0]);

        this.scale = scale;
        this.width = (int) image.getWidth() * scale;
        this.height = (int) image.getHeight() * scale;
    }

    public void setAmountOfSprites(int amount) {
      pathsToSprite = new String[amount];
    }

    public void setPathsToSprite(String[] spritePaths) {
        for (int i = 0; i < pathsToSprite.length; i++) {
            setPathToSprite(spritePaths[i], i);
        }
        setSprite(pathsToSprite[0]);
    }

    public void setPathToSprite(String path, int placement) {
        pathsToSprite[placement] = path;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getScale() {
        return scale;
    }

    public Image getImage() {
        return image;
    }

    public void setSprite(String pathToSprite) {
        try {
            image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(pathToSprite)));
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

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }

    @Override
    public EntityComponentTypes getType() {
        return EntityComponentTypes.SPRITE;
    }

    @Override
    public void setEntity(Entity entity) {
    }

    @Override
    public void process(GameData gameData, World world) {
    }
}
