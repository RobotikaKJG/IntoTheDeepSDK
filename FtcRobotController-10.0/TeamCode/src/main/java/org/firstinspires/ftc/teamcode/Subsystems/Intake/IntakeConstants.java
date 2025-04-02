package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;

public class IntakeConstants {
    public static final double intakeSpeed = 1;
    public static final double intakeSlowSpeed = 0.8;
    public static double getIntakeSpeed(){
        return GlobalVariables.slowMode ? intakeSlowSpeed : intakeSpeed;
    }
    public static final double currentLimit = 5;//3.35;

    public static final int extendoMaxExtension = 600;
    public static final int extendoMinExtension = 300;

    public static final double intakeServoMaxPos = 0.15;     // Taking position
    public static final double intakeServoMinPos = 0;     // Minimum rotational position, starting position

    public static final double secureSampleTime = 0.5;
    public static final double intakePushoutTime = 0.35;
    public static final double sampleClawCloseTime = 0.3;

}
