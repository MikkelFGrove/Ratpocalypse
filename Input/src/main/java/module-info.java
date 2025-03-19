import dk.lima.common.input.IInputSPI;
import dk.lima.input.KeyPressHandler;
import dk.lima.input.KeyReleaseHandler;
import dk.lima.input.MouseMovedHandler;

module Input {
    requires Common;
    requires CommonInput;
    requires javafx.graphics;
    requires java.desktop;
    provides IInputSPI with
            KeyPressHandler,
            KeyReleaseHandler,
            MouseMovedHandler;
}


