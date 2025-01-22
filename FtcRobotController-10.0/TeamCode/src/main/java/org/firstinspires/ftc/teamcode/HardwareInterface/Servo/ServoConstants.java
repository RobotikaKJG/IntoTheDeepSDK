package org.firstinspires.ftc.teamcode.HardwareInterface.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;

public class ServoConstants {
    // Servo indexes
    public static final int outtakeLeft = 0;
    public static final int outtakeRight = 1;
    public static final int release = 2;
    public static final int specimenClaw = 3;
    public static final int intake = 4;

    public static final double[] servoMinPos =
            {
                    OuttakeConstants.outtakeLeftServoMinPos,
                    OuttakeConstants.outtakeRightServoMinPos,
                    OuttakeConstants.releaseServoMinPos,
                    OuttakeConstants.specimenClawServoMinPos,
                    IntakeConstants.intakeServoMinPos
            };
    public static final double[] servoMaxPos =
            {
                    OuttakeConstants.outtakeLeftServoMaxPos,
                    OuttakeConstants.outtakeRightServoMaxPos,
                    OuttakeConstants.releaseServoMaxPos,
                    OuttakeConstants.specimenClawServoMaxPos,
                    IntakeConstants.intakeServoMaxPos
            };
}
