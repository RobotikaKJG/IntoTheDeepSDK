package org.firstinspires.ftc.teamcode.HardwareInterface;

import org.firstinspires.ftc.teamcode.Subsystems.Drone.DroneConstants;
import org.firstinspires.ftc.teamcode.Subsystems.OldIntake.OldIntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.OldOuttake.OldOuttakeConstants;

public class ServoConstants {
    // Servo indexes
    public static final int intake = 0;
    public static final int outtake = 1;
    public static final int release1 = 2;
    public static final int release2 = 3;
    public static final int drone = 4;
    public static final double[] servoMinPos =
            {
                    OldIntakeConstants.intakeServoMinPos,
                    OldOuttakeConstants.outtakeServoMinPos,
                    OldOuttakeConstants.releaseServoMinPos,
                    OldOuttakeConstants.releaseServoMinPos,
                    DroneConstants.droneServoMinPos
            };
    public static final double[] servoMaxPos =
            {
                    OldIntakeConstants.intakeServoMaxPos,
                    OldOuttakeConstants.outtakeServoMaxPos,
                    OldOuttakeConstants.releaseServoMaxPos,
                    OldOuttakeConstants.releaseServoMaxPos,
                    DroneConstants.droneServoMaxPos
            };
    public static final double[] servoIncrement =
            {
                    OldIntakeConstants.intakeServoIncrement,
                    OldOuttakeConstants.outtakeServoIncrement,
                    OldOuttakeConstants.releaseServoIncrement,
                    OldOuttakeConstants.releaseServoIncrement,
                    DroneConstants.droneServoIncrement
            };
}
