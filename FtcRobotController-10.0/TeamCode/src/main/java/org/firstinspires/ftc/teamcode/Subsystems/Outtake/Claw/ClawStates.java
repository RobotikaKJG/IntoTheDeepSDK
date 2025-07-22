package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw;

public enum ClawStates {
    closed, //when sample is taken
    freeMove, // closed but sample can slide around
    halfOpen, //when releasing sample
    fullyOpen //when waiting to take sample, starting pos
}
