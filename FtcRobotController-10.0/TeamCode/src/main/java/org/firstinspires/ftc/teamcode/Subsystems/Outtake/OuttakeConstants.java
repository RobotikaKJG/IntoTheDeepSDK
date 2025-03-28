package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

public class OuttakeConstants {
    public static final double outtakeLeftServoMaxPos = 0.825;//0.82;//0.88;//0.965;//+0.0?     // Taking position, starting position
    public static final double outtakeLeftServoMinPos = 0.225;//0.065;//1675;//0.2;     // Placing position,
    public static final double releaseServoMaxPos = 0.80;     // Maximum rotational position, claw open
    public static final double releaseServoReleasePos = 0.65;     // used for upOpen
    public static final double releaseServoMinPos = 0.48;     // Minimum rotational position, claw closed position
    public static final double outtakeArmWait = 0.6;
    public static final double outtakeArmCloseWait = 0.5;//0.65;
    public static final int lowBasketPos = 425;
    public static final int highBasketPos = 720;
    public static final int hangBar = 500;
    public static final int slideTargetThreshold = 20;


    //round needed to prevent floating point errors
    public static final double outtakeRightServoMaxPos = 0.840;     // Maximum rotational position
    public static final double outtakeRightServoMinPos = 0.246;     // Taking position, starting position

    public static double releaseServoWait = 0.3;

    public static double specimenClawServoMinPos = 0.5;
    public static double specimenClawServoMaxPos = 0.7;

    public static double specimenReleaseWait = 0.1;

    public static int lowRungPos = 300;
    public static int highRungPos = 1350;
    public static int lowRungScorePos = 0;
    public static int highRungScorePos = 1040;
    public static int specimenHungThreshold = 10;
    public static int hangThreshold = 20;
}
