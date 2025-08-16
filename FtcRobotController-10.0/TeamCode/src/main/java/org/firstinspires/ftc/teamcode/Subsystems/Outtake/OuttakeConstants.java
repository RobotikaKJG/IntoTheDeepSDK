package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

public class OuttakeConstants {
    public static  double outtakeLeftServoMaxPos = 0.7;//0.7;//0.805;//0.82;//0.88;//0.965;//+0.0?     // up
    public static double outtakeLeftServoStartPos = 0.1735;
    public static  double outtakeLeftServoIntakePos = 0.1135;
    public static  double outtakeLeftServoMinPos = 0.0885;//.1;//0.065;//1675;//0.2;     // down
    public static final double releaseServoMaxPos = 0.7;     // Maximum rotational position, claw open
    public static final double releaseServoReleasePos = 0.7;     // used for upOpen
    public static final double releaseServoFreeMovePos = 0.28;     // Minimum rotational position, claw closed position
    public static final double releaseServoMinPos = 0.24;     // Minimum rotational position, claw closed position
    public static final double outtakeArmWait = 0.5;
    public static final double outtakeArmCloseWait = 0;//0.65;
    public static final int lowBasketPos = 200;
    public static final int highBasketPos = 620;
    public static final int hangBar = 60;
    public static final int slideTargetThreshold = 20;


    public static double outtakeRightServoMaxPos = 0.7365;//0.793;     // up
    public static double outtakeRightServoStartPos = 0.21;
    public static double outtakeRightServoIntakePos = 0.15;
    public static double outtakeRightServoMinPos = 0.125;//.082;     //  down

    public static double releaseServoWait = 0.15;

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
    public static int limitSwitchThreshold = 200; // motion profiling turns off, the closing finishes with limit switch seeking
    public static int limitSwitchRetractionStep = 10;
    
    public static double takeSpecimenWait = 0.2;
    public static double ejectWait = 0.5;
}
