import dk.lima.common.services.ITimeTask;

module Timer {
    requires Common;

    provides ITimeTask with dk.lima.timer.ClockTask;
}