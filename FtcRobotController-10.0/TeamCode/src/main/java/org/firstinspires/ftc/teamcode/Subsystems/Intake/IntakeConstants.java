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

    public static final double intakeServoMaxPos = 0.7;     // up
    public static final double intakeServoSubPos = 0.5;     // middle
    public static final double intakeServoMinPos = 0.15;     // down

    public static final double secureSampleTime = 0;//.3;
    public static final double secureSampleAutonTime = 0;//.05;
    public static final double intakeAutonomousPushoutTime = 0.1;
    public static double intakePushoutTime = 0.15;
    public static final double sampleClawCloseTime = 0.2;
    public static final double pivotWaitTime = 1; //too long, NIGHTNOTE

    public static double lockServoMinPos = 0.75;
    public static double lockServoMaxPos = 1; // closed
}
