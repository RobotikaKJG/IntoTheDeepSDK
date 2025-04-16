package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;

public class IntakeConstants {
    public static  double intakeSpeed = 1;
    public static final double intakeSlowSpeed = 0.8;

    public static double getIntakeSpeed(){
        return GlobalVariables.slowMode ? intakeSlowSpeed : intakeSpeed;
    }
    public static void setIntakeSpeed(double speed){
        intakeSpeed = speed;
    }
    public static final double currentLimit = 5;//3.35;

    public static final int extendoMaxExtension = 550;
    public static final int extendoSampleExtension = 380;
    public static int extendoSpecimenExtension = 480;
    public static final int extendoMinExtension = 300;
    public static final int extendoThreshold = 20;

    public static final double servoSpeed = 1;

    public static final double intakeServoMaxPos = 0.15;     // Taking position
    public static final double intakeServoMinPos = 0;     // Minimum rotational position, starting position

    public static final double secureSampleTime = 0.3;
    public static final double secureSampleAutonTime = 0.3;
    public static final double intakePushoutTime = 0.1;
    public static double intakePushoutAutoTime = 0.15;
    public static final double sampleClawCloseTime = 0.3;

    public static double lockServoMinPos = 0.5;
    public static double lockServoMaxPos = 0.95; // closed
}
