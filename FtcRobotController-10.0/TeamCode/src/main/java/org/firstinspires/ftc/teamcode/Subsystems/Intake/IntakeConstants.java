package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import android.graphics.Color;

import org.firstinspires.ftc.teamcode.Enums.Alliance;
import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;

public class IntakeConstants {
    public static final int yellow = Color.rgb(170,130,80);
    public static final int yellowThreshold = 50;
    public static final int red = Color.rgb(120, 60, 60);
    public static final int redThreshold = 30;
    public static final int blue = Color.rgb(40,60,65);
    public static final int blueThreshold = 12;
    private static final boolean isRedAlliance = GlobalVariables.alliance == Alliance.Red;
    public static final int allianceColor = isRedAlliance ? red : blue; //fancy ahh declaration
    public static final int allianceThreshold = isRedAlliance ? redThreshold : blueThreshold;
    public static final int otherAllianceColor = isRedAlliance ? blue:red;
    public static final int otherAllianceThreshold = isRedAlliance ? blueThreshold:redThreshold;

    public static final double intakeSpeed = 1;
    public static final double servoSpeed = 1;

    public static final double intakeServoIncrement = 0.2;     // amount to slew servo each cycle, VERY MUCH USED
    public static final double intakeServoMaxPos = 0.15;     // Taking position
    public static final double intakeServoMinPos = 0;     // Minimum rotational position, starting position

    public static final double secureSampleTime = 0.4;
    public static final double intakePushoutTime = 0.75;
    public static final double pushToEndTime = 0.6;
    public static final double clawCloseTime = 0.6;

    public static final GamepadIndexValues closeButton = GamepadIndexValues.leftBumper;
    public static final GamepadIndexValues forwardButton = GamepadIndexValues.rightBumper;
    public static final GamepadIndexValues backButton = GamepadIndexValues.rightTrigger;
    public static final GamepadIndexValues motorButton = GamepadIndexValues.leftTrigger;
    public static final GamepadIndexValues reverseButton = GamepadIndexValues.dpadUp;
}
