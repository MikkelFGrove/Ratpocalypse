package dk.lima.common.input;

import dk.lima.common.data.GameData;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;

public interface IInputSPI {
    EventType<? extends InputEvent> getInputEvent();

    EventHandler<InputEvent> getInputHandler(GameData gameData);
}
