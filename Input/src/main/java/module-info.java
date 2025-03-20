import dk.lima.common.input.IInputSPI;
import dk.lima.input.*;

module Input {
    requires Common;
    requires CommonInput;
    requires javafx.graphics;

    provides IInputSPI with
            KeyPressHandler,
            KeyReleaseHandler,
            MouseMovedHandler,
            MouseDragHandler,
            MousePressHandler,
            MouseReleaseHandler;
}


