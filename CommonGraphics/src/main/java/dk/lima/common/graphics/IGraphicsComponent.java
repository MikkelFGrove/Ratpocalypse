package dk.lima.common.graphics;

import dk.lima.common.data.GameData;
import javafx.scene.Node;

public interface IGraphicsComponent {
    Node createComponent(GameData gameData);
    void updateComponent(GameData gameData);
    void showComponent(Boolean shouldShow);
}
