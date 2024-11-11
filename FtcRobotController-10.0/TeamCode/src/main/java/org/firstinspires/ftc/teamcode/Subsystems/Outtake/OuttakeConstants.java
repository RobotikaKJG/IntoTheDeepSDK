package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;

public class OuttakeConstants {
    public static final double outtakeLeftServoIncrement = 0.57;     // amount to slew servo each cycle, VERY MUCH USED
    public static final double outtakeLeftServoMaxPos = 0.95;     // Taking position, starting position
    public static final double outtakeLeftServoMinPos = 0.2;     // Minimum rotational position
    public static final double outtakeLeftServoIdlePos = 0.93;     // Position when outtake is off
    public static final double releaseServoIncrement = 0.2;     // amount to slew servo each cycle
    public static final double releaseServoMaxPos = 0.48;     // Maximum rotational position
    public static final double releaseServoReleasePos = 0.2;     // used for upOpen
    public static final double releaseServoMinPos = 0.03;     // Minimum rotational position, starting position
    public static final double outtakeServoWait = 1;
    public static final int lowBasketPos = 2700;
    public static final int highBasketPos = 4300;

    public static final double outtakeRightServoIncrement = 0.57;     // amount to slew servo each cycle, VERY MUCH USED
    //round needed to prevent floating point errors
    public static final double outtakeRightServoMaxPos = Math.round((1-OuttakeConstants.outtakeLeftServoMinPos)*100.0)/100.0;     // Maximum rotational position
    public static final double outtakeRightServoMinPos = Math.round((1-OuttakeConstants.outtakeLeftServoMaxPos)*100.0)/100.0;     // Taking position, starting position
    public static final double outtakeRightServoIdlePos = Math.round((1-OuttakeConstants.outtakeLeftServoIdlePos)*100.0)/100.0;     // Position when outtake is off

    public static final GamepadIndexValues upButton = GamepadIndexValues.leftBumper;
    public static final GamepadIndexValues downButton = GamepadIndexValues.leftTrigger;
    public static final GamepadIndexValues releaseButton = GamepadIndexValues.square; //could also act as closebutton?
    public static double releaseServoCloseWait = 0.7;
}
