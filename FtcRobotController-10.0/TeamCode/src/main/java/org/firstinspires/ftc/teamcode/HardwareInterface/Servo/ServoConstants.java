package org.firstinspires.ftc.teamcode.HardwareInterface.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;

public class ServoConstants {
    // Servo indexes
    public static final int outtake = 0;
    public static final int outtakePivot = 1;
    public static final int release = 2;
    public static final int intake = 3;


    public static final double[] servoMinPos =
            {
                    OuttakeConstants.outtakeServoMinPos,
                    OuttakeConstants.outtakePivotServoMinPos,
                    OuttakeConstants.releaseServoMinPos,
                    IntakeConstants.intakeServoMinPos
            };
    public static final double[] servoMaxPos =
            {
                    OuttakeConstants.outtakeServoMaxPos,
                    OuttakeConstants.outtakePivotServoMaxPos,
                    OuttakeConstants.releaseServoMaxPos,
                    IntakeConstants.intakeServoMaxPos
            };
}
