package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

public class OuttakeConstants {
    public static  double outtakeServoMaxPos = 0.72;//0.82;//0.88;//0.965;//+0.0?     // Taking position, starting position
    public static  double outtakeServoOverIntakePos = 0.1;
    public static  double outtakeServoMinPos = 0.03;//0.065;//1675;//0.2;     // Placing position,
    public static final double releaseServoMaxPos = 0.2;     // Maximum rotational position, claw open
    public static final double releaseServoReleasePos = 0.25;     // open but clears arm, idk when could be needed
    public static final double releaseServoFreeMovePos = 0.55;     // Minimum rotational position, claw closed position
    public static final double releaseServoMinPos = 0.1;     // Minimum rotational position, claw closed position
    public static final double outtakeArmWait = 0.6;
    public static final double outtakeArmCloseWait = 0.5;//0.65;
    public static final int lowBasketPos = 200;
    public static final int highBasketPos = 650;
    public static final int hangBar = 60;
    public static final int slideTargetThreshold = 20;


    public static double outtakePivotServoMaxPos = 0.93;     // used for idling servo to not hit intake
    public static double outtakePivotServoTakePos = 0.8;
    public static double outtakePivotServoMinPos = 0.63;//0.63;     //  ending position 0,246

    public static double releaseServoWait = 0.3;

    public static double specimenClawServoMinPos = 0.18;
    public static double specimenClawServoMaxPos = 0.5; // closed

    public static double specimenReleaseWait = 0.1;

    public static int lowRungPos = 200; // Currently used for sample drop too, NOTE
    public static int highRungPos = 900;
    public static int lowRungScorePos = 40;
    public static int highRungScorePos = 360;
    public static int specimenHungThreshold = 10;
    public static int hangThreshold = 20;

    public static double speedProfileMultiplier = 0.015;
    public static int profilingThreshold = 300;
    public static double verticalOffset = -3.5;
    public static int limitSwitchThreshold = 50; // motion profiling turns off, the closing finishes with limit switch seeking
    public static int limitSwitchRetractionStep = 10;
    
    public static double takeSpecimenWait = 0.2;
    public static double ejectWait = 0.5;
    public static double rotateOuttakeArmWait = 0.2;
    public static double clawCloseWait = 0.3;
    public static int slidesClearIntakeHeight = 400;
    public static double rotateIntakePivotWait = 0.3;
}
