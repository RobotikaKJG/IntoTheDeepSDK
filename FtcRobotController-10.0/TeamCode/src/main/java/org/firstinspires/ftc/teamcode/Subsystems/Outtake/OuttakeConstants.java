package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

public class OuttakeConstants {
    public static final double outtakeLeftServoMaxPos = 0.825;//0.82;//0.88;//0.965;//+0.0?     // Taking position, starting position
    public static final double outtakeLeftServoMinPos = 0.225;//0.065;//1675;//0.2;     // Placing position,
    public static final double releaseServoMaxPos = 0.70;     // Maximum rotational position, claw open
    public static final double releaseServoReleasePos = 0.65;     // used for upOpen
    public static final double releaseServoMinPos = 0.48;     // Minimum rotational position, claw closed position
    public static final double outtakeArmWait = 0.6;
    public static final double outtakeArmCloseWait = 0.5;//0.65;
    public static final int lowBasketPos = 425;
    public static final int highBasketPos = 720;
    public static final int hangBar = 500;
    public static final int slideTargetThreshold = 20;


    public static final double outtakeRightServoMaxPos = 0.84;     // Taking position,Maximum rotational position 0,84
    public static final double outtakeRightServoSamplePos = 0.246;
    public static final double outtakeRightServoMinPos = 0.1;     //  ending position 0,246

    public static double releaseServoWait = 0.3;

    public static double specimenClawServoMinPos = 0.2;
    public static double specimenClawServoMaxPos = 0.5;

    public static double specimenReleaseWait = 0.1;

    public static int lowRungPos = 200; // Currently used for sample drop too, NOTE
    public static int highRungPos = 500;
    public static int lowRungScorePos = 40;
    public static int highRungScorePos = 380;
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
