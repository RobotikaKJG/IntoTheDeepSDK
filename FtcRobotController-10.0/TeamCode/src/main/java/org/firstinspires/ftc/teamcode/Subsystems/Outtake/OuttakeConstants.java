package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

public class OuttakeConstants {
    public static  double outtakeLeftServoMaxPos = 0.834;//0.82;//0.88;//0.965;//+0.0?     // Taking position, starting position
    public static  double outtakeLeftServoSamplePos = 0.257;
    public static  double outtakeLeftServoMinPos = 0.1;//0.065;//1675;//0.2;     // Placing position,
    public static final double releaseServoMaxPos = 0.68;     // Maximum rotational position, claw open
    public static final double releaseServoReleasePos = 0.63;     // used for upOpen
    public static final double releaseServoFreeMovePos = 0.48;     // Minimum rotational position, claw closed position
    public static final double releaseServoMinPos = 0.46;     // Minimum rotational position, claw closed position
    public static final double outtakeArmWait = 0.6;
    public static final double outtakeArmCloseWait = 0.5;//0.65;
    public static final int lowBasketPos = 425;
    public static final int highBasketPos = 720;
    public static final int hangBar = 300;
    public static final int slideTargetThreshold = 20;


    public static double outtakeRightServoMaxPos = 0.822;     // Taking position,Maximum rotational position 0,84
    public static double outtakeRightServoSamplePos = 0.241;
    public static double outtakeRightServoMinPos = 0.082;     //  ending position 0,246

    public static double releaseServoWait = 0.3;

    public static double specimenClawServoMinPos = 0.18;
    public static double specimenClawServoMaxPos = 0.5; // closed

    public static double specimenReleaseWait = 0.1;

    public static int lowRungPos = 200; // Currently used for sample drop too, NOTE
    public static int highRungPos = 485;
    public static int lowRungScorePos = 40;
    public static int highRungScorePos = 360;
    public static int specimenHungThreshold = 10;
    public static int hangThreshold = 20;

    public static double speedProfileMultiplier = 0.015;
    public static int profilingThreshold = 300;
    public static double verticalOffset = -3.5;
    public static int limitSwitchThreshold = 150; // motion profiling turns off, the closing finishes with limit switch seeking
    public static int limitSwitchRetractionStep = 10;
    public static double takeSpecimenWait = 0.2;
    public static double ejectWait = 0.5;
}
