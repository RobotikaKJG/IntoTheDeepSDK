package org.firstinspires.ftc.teamcode.Subsystems.Intake;

public class IntakeConstants {
    public static final double intakeServoMaxPos = 0.769;     // Maximum rotational position, claw open
    public static final double intakeServoMinPos = 0.469;     // Minimum rotational position, claw closed position

    public static int slideMaxExtention = 1669;
    public static int slideMinExtention = 0;
    public static int slideExtentionStep = 250;
    public static int slideFirstExtentionStep = 669;

    public static int pivotMaxAngle = 2690;
    public static int pivotSlightlyRaisedAngle = 269;

    public static int hangThreshold = 50;

    public static int limitSwitchThreshold = 69; // motion profiling turns off, the closing finishes with limit switch seeking
    public static int limitSwitchRetractionStep = 10;

    public static double secureSampleWait = 0.3;
    public static double pivotDownSpeed = 0.3;
    public static double sampleEjectWait = 1;
}
