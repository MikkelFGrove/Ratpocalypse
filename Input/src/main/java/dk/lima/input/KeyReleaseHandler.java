package dk.lima.input;

import dk.lima.common.data.EGameInputs;
import dk.lima.common.data.GameData;
import dk.lima.common.input.IInputSPI;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyReleaseHandler implements IInputSPI {
    @Override
    public EventType<? extends InputEvent> getInputEvent() {
        return KeyEvent.KEY_RELEASED;
    }

    @Override
    public EventHandler<InputEvent> getInputHandler(GameData gameData) {
        return new EventHandler<InputEvent>() {
            @Override
            public void handle(InputEvent inputEvent) {
                if (inputEvent instanceof KeyEvent keyEvent) {
                    switch (keyEvent.getCode()) {
                        case UP, KeyCode.W -> gameData.getInputs().setInput(EGameInputs.UP, false);
                        case DOWN, KeyCode.S -> gameData.getInputs().setInput(EGameInputs.DOWN, false);
                        case LEFT, KeyCode.A -> gameData.getInputs().setInput(EGameInputs.LEFT, false);
                        case RIGHT, KeyCode.D -> gameData.getInputs().setInput(EGameInputs.RIGHT, false);
                        case SPACE -> gameData.getInputs().setInput(EGameInputs.ACTION, false);
                    }
                }
            }
        };
    }
}