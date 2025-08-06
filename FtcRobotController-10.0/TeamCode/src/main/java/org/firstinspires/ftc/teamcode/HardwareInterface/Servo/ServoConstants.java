package org.firstinspires.ftc.teamcode.HardwareInterface.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;

public class ServoConstants {
    // Servo indexes
    public static final int outtakeLeft = 0;
    public static final int outtakeRight = 1;
    public static final int release = 2;
    public static final int specimenClaw = 3;
    public static final int intake = 4;
    public static final int lock = 5; // Sample Lock Servo


    public static final double[] servoMinPos =
            {
                    IntakeConstants.outtakeLeftServoMinPos,
                    IntakeConstants.outtakeRightServoMinPos,
                    IntakeConstants.releaseServoMinPos,
                    IntakeConstants.specimenClawServoMinPos,
            };
    public static final double[] servoMaxPos =
            {
                    IntakeConstants.outtakeLeftServoMaxPos,
                    IntakeConstants.outtakeRightServoMaxPos,
                    IntakeConstants.releaseServoMaxPos,
                    IntakeConstants.specimenClawServoMaxPos,
            };
}
