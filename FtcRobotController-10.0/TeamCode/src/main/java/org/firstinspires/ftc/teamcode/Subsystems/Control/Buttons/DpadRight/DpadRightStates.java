package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

public enum DpadRightStates {
    toggleHang,  // Command to toggle the hang state
    idle;        // No command

    private static DpadRightStates currentState = idle;

    public static DpadRightStates getDpadRightState() {
        return currentState;
    }

    public static void setDpadRightState(DpadRightStates state) {
        currentState = state;
    }
}
