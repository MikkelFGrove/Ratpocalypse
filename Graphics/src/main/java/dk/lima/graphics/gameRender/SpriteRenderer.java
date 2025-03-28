package dk.lima.graphics.gameRender;

import dk.lima.common.data.Entity;
import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import dk.lima.common.graphics.IGraphicsService;
import dk.lima.common.player.Player;

import javafx.scene.layout.Pane;
import javafx.scene.Node;

public class SpriteRenderer implements IGraphicsService {

    private Pane spritePane;

    @Override
    public Node createComponent(GameData gameData, World world) {
        spritePane = new Pane();

        for(Entity entity: world.getEntities()) {
            entity.get
        }

        return spritePane;
    }

    @Override
    public void updateComponent(GameData gameData, World world) {
        for(Entity )
    }

    @Override
    public void showComponent(Boolean shouldShow) {

    }
}