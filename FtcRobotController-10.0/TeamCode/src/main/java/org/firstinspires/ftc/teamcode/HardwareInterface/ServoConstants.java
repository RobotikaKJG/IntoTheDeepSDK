package org.firstinspires.ftc.teamcode.HardwareInterface;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;

public class ServoConstants {
    // Servo indexes
    public static final int outtakeLeft = 0;
    public static final int outtakeRight = 1;
    public static final int release = 2;
    public static final double[] servoMinPos =
            {
                    OuttakeConstants.outtakeLeftServoMinPos,
                    OuttakeConstants.outtakeRightServoMinPos,
                    OuttakeConstants.releaseServoMinPos
            };
    public static final double[] servoMaxPos =
            {
                    OuttakeConstants.outtakeLeftServoMaxPos,
                    OuttakeConstants.outtakeRightServoMaxPos,
                    OuttakeConstants.releaseServoMaxPos
            };
    public static final double[] servoIncrement =
            {
                    OuttakeConstants.outtakeLeftServoIncrement,
                    OuttakeConstants.outtakeRightServoIncrement,
                    OuttakeConstants.releaseServoIncrement
            };
}
