package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang;

public enum HangStates {
    retracted,   // Slides are retracted (default position)
    extending,   // Command to extend the slides to high basket
    extended,    // Slides have reached the high basket position and are holding
    retracting  // Command to retract the slides
}
