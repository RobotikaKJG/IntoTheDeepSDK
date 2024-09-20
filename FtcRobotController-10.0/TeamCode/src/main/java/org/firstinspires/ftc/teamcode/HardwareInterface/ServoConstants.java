package org.firstinspires.ftc.teamcode.HardwareInterface;

import org.firstinspires.ftc.teamcode.Subsystems.Drone.DroneConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;

public class ServoConstants {
    // Servo indexes
    public static final int intake = 0;
    public static final int outtake = 1;
    public static final int release1 = 2;
    public static final int release2 = 3;
    public static final int drone = 4;
    public static final double[] servoMinPos =
            {
                    IntakeConstants.intakeServoMinPos,
                    OuttakeConstants.outtakeServoMinPos,
                    OuttakeConstants.releaseServoMinPos,
                    OuttakeConstants.releaseServoMinPos,
                    DroneConstants.droneServoMinPos
            };
    public static final double[] servoMaxPos =
            {
                    IntakeConstants.intakeServoMaxPos,
                    OuttakeConstants.outtakeServoMaxPos,
                    OuttakeConstants.releaseServoMaxPos,
                    OuttakeConstants.releaseServoMaxPos,
                    DroneConstants.droneServoMaxPos
            };
    public static final double[] servoIncrement =
            {
                    IntakeConstants.intakeServoIncrement,
                    OuttakeConstants.outtakeServoIncrement,
                    OuttakeConstants.releaseServoIncrement,
                    OuttakeConstants.releaseServoIncrement,
                    DroneConstants.droneServoIncrement
            };
}
