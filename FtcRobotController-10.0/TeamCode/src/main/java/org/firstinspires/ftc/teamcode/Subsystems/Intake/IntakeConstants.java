package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;

public class IntakeConstants {
    public static final double intakeSpeed = 1;
    public static final double intakeSlowSpeed = 0.8;
    public static double getIntakeSpeed(){
        return GlobalVariables.slowMode ? intakeSlowSpeed : intakeSpeed;
    }
    public static final double currentLimit = 3.35;

    public static final int extendoMaxExtension = 1650;
    public static final int extendoMinExtension = 800;
    public static final int extendoThreshold = 20;

    public static final double servoSpeed = 1;

    public static final double intakeServoMaxPos = 0.15;     // Taking position
    public static final double intakeServoMinPos = 0;     // Minimum rotational position, starting position

    public static final double secureSampleTime = 0.4;
    public static final double intakePushoutTime = 0.75;
    public static final double clawCloseTime = 0.3;
}
