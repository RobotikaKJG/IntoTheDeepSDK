package org.firstinspires.ftc.teamcode.Subsystems.OldIntake;

public class OldIntakeConstants {
    public static final double intakeServoIncrement = 0.05;     // amount to slew servo each cycle
    public static final double intakeServoMaxPos = 0.60;     // Maximum rotational position
    public static final double intakeServoMinPos = 0.37;    // Minimum rotational position, starting position
    public static final double intakeServoAutonStartPos = 0.30; // only used for autonomous start to fit in the size boundaries
    public static final double intakeStopServoCloseWait = 0.1;
    public static final double intakeStopPushoutWait = 0.6;
    public static final double intakeSpeed = -1; //speed of the intake motor
}
