package org.firstinspires.ftc.teamcode.Subsystems.Intake;

public class IntakeConstants {
    public static final double intakeServoMaxPos = 0.73;     // Maximum rotational position, claw open
    public static final double intakeServoMinPos = 0.50;     // Minimum rotational position, claw closed position

    public static int limitSwitchThreshold = 200; // motion profiling turns off, the closing finishes with limit switch seeking
    public static int limitSwitchRetractionStep = 10;

    public static int slideMaxExtention = 1000;
    public static int slideMinExtention = 0;
    public static int slideExtentionStep = 100;
    public static int slideFirstExtentionStep = 500;

    public static double takeSpecimenWait = 0.2;
    public static double ejectWait = 0.5;
}
