package dk.lima.input;

import dk.lima.common.data.Coordinate;
import dk.lima.common.data.GameData;
import dk.lima.common.input.IInputSPI;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;

public class MouseDragHandler implements IInputSPI {
    @Override
    public EventType<? extends InputEvent> getInputEvent() {
        return MouseEvent.MOUSE_DRAGGED;
    }

    @Override
    public EventHandler<InputEvent> getInputHandler(GameData gameData) {
        return new EventHandler<>() {
            @Override
            public void handle(InputEvent inputEvent) {
                if (inputEvent instanceof MouseEvent mouseEvent) {
                    gameData.setMousePosition(new Coordinate(mouseEvent.getSceneX(), mouseEvent.getSceneY()));
                }
            }
        };
    }
}
