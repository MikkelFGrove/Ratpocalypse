package dk.lima.common.graphics;

import dk.lima.common.data.GameData;
import dk.lima.common.data.World;
import javafx.scene.Node;

public interface IGraphicsComponent {
    Node createComponent(GameData gameData, World world);
    void updateComponent(GameData gameData, World world);
    void showComponent(Boolean shouldShow);
}
