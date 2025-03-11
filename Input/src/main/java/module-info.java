import dk.lima.common.input.IInputSPI;

module Input {
    requires Common;
    requires CommonInput;
    requires javafx.graphics;
    provides IInputSPI with dk.lima.input.InputHandler;
}


