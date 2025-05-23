package dk.lima.input;

import dk.lima.common.data.EGameInputs;
import dk.lima.common.data.GameData;
import dk.lima.common.input.IInputSPI;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyPressHandler implements IInputSPI {
    @Override
    public EventType<? extends InputEvent> getInputEvent() {
        return KeyEvent.KEY_PRESSED;
    }

    @Override
    public EventHandler<InputEvent> getInputHandler(GameData gameData) {
        return new EventHandler<>() {
            @Override
            public void handle(InputEvent inputEvent) {
                if (inputEvent instanceof KeyEvent keyEvent) {
                    switch (keyEvent.getCode()) {
                        case UP, KeyCode.W -> gameData.getInputs().setInput(EGameInputs.UP, true);
                        case LEFT, KeyCode.A -> gameData.getInputs().setInput(EGameInputs.LEFT, true);
                        case DOWN, KeyCode.S -> gameData.getInputs().setInput(EGameInputs.DOWN, true);
                        case RIGHT, KeyCode.D -> gameData.getInputs().setInput(EGameInputs.RIGHT, true);
                        case SPACE -> gameData.getInputs().setInput(EGameInputs.ACTION, true);
                        case ESCAPE -> gameData.getInputs().setInput(EGameInputs.PAUSE, true);
                        case KeyCode.B -> gameData.getInputs().setInput(EGameInputs.DEBUG, true);
                    }
                }
            }
        };
    }
}
