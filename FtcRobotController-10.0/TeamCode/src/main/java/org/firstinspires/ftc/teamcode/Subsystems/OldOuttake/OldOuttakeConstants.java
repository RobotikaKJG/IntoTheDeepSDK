package org.firstinspires.ftc.teamcode.Subsystems.OldOuttake;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;

public class OldOuttakeConstants {
    public static final int slideTeleOpMinExtension = 950; // minimum extension position, first snap pos for outtake macro
    public static final int slideExtensionStep = 270;
    public static final int outtakeServoOpenHeight = 200;
    public static final double outtakeServoIncrement = 0.57;     // amount to slew servo each cycle, VERY MUCH USED
    public static final double outtakeServoMaxPos = 1;//0.91;     // Maximum rotational position, starting position
    public static final double outtakeServoMinPos = 0.34;     // Minimum rotational position
    public static final double releaseServoIncrement = 0.6;     // amount to slew servo each cycle
    public static final double releaseServoMaxPos = 0.8;     // Maximum rotational position
    public static final double releaseServoMinPos = 0.2;     // Minimum rotational position, starting position
    public static final double outtakeServoCloseWait = 0.45;
    public static int slideCurrentMinExtension = slideTeleOpMinExtension;

    public static final GamepadIndexValues closeButton = GamepadIndexValues.leftBumper;
    public static final GamepadIndexValues upButton = GamepadIndexValues.rightBumper;
    public static final GamepadIndexValues downButton = GamepadIndexValues.rightTrigger;
    public static final GamepadIndexValues releaseButton = GamepadIndexValues.square;
}
