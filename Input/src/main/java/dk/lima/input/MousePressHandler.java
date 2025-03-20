package dk.lima.input;

import dk.lima.common.data.EGameInputs;
import dk.lima.common.data.GameData;
import dk.lima.common.input.IInputSPI;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MousePressHandler implements IInputSPI {
    @Override
    public EventType<? extends InputEvent> getInputEvent() {
        return MouseEvent.MOUSE_PRESSED;
    }

    @Override
    public EventHandler<InputEvent> getInputHandler(GameData gameData) {
        return new EventHandler<>() {
            @Override
            public void handle(InputEvent inputEvent) {
                if (inputEvent instanceof MouseEvent mouseEvent) {
                    if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                        gameData.getInputs().setInput(EGameInputs.ACTION, true);
                    }
                }
            }
        };
    }
}
