package dk.lima.common.entitycomponents;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.entity.IEntityComponent;

import javafx.scene.image.Image;

public class SpriteCP implements IEntityComponent {
    public Image image;

    private float posX, posY;

    private String[] pathsToSprite;

    public SpriteCP(int amount) {
        setAmountOfSprites(amount);
        getImage(pathsToSprite);
    }

    public void setAmountOfSprites(int amount) {
      pathsToSprite = new String[amount];
    }

    public void setPathsToSprite(String path, int placement) {
        pathsToSprite[placement] = path;
    }


    private void getImage(String[] pathToSprite) {
        image =new Image(getClass().getResourceAsStream(pathToSprite[0]));
    }



    public void setPosX(float x) {
        posX = x;
    }
    public void setPosY(float y) {
        posY = y;
    }
    public float getPosX() {
        return posX;
    }
    public float getPosY() {
        return posY;
    }
    
    @Override
    public void process(GameData gameData, World world) {

    }
}
