package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides;

public enum HangStates {
    RETRACTED,   // Slides are retracted (default position)
    EXTENDING,   // Command to extend the slides to high basket
    EXTENDED,    // Slides have reached the high basket position and are holding
    RETRACTING;  // Command to retract the slides

    // Static variable to hold the current hang state (default is RETRACTED)
    private static HangStates currentState = RETRACTED;

    public static HangStates getHangState() {
        return currentState;
    }

    public static void setHangState(HangStates state) {
        currentState = state;
    }
}
