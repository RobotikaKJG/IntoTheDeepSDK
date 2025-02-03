package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

public class OuttakeConstants {
    public static final double outtakeLeftServoMaxPos = 0.88;//0.965;//+0.0?     // Placing position,
    public static final double outtakeLeftServoMinPos = 0.1675;//0.2;     // Taking position, starting position
    public static final double releaseServoMaxPos = 0.77;     // Maximum rotational position, claw open
    public static final double releaseServoReleasePos = 0.60;     // used for upOpen
    public static final double releaseServoMinPos = 0.45;     // Minimum rotational position, claw closed position
    public static final double outtakeArmWait = 0.8;
    public static final double outtakeArmCloseWait = 0.8;//0.65;
    public static final int lowBasketPos = 1100;
    public static final int highBasketPos = 2100;
    public static final int slideTargetThreshold = 20;

    //round needed to prevent floating point errors
    public static final double outtakeRightServoMaxPos = Math.round((1-OuttakeConstants.outtakeLeftServoMinPos)*100.0)/100.0;     // Maximum rotational position
    public static final double outtakeRightServoMinPos = Math.round((1-OuttakeConstants.outtakeLeftServoMaxPos)*100.0)/100.0;     // Taking position, starting position

    public static double releaseServoWait = 0.3;

    public static double specimenClawServoMinPos = 0.45;
    public static double specimenClawServoMaxPos = 0.75;

    public static double specimenClipOnWait = 1;
    public static double specimenReleaseWait = 0.5;

    public static int lowRungPos = 800;
    public static int highRungPos = 1500;
    public static int lowRungScorePos = 750;
    public static int highRungScorePos = 1450;
}
