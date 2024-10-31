package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import android.graphics.Color;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;

public class IntakeConstants {
    public static final int slideExtensionStep = 370;
    public static final int targetColor = Color.rgb(40, 48, 45);
    public static final int threshold = 20;
    public static final double intakeSpeed = 0.6;
    public static final double servoSpeed = 0.4;

    public static final double secureSampleTime = 0;
    public static final double intakePushoutTime = 1;

//    public static final int autoClose = 0;
//    public static final int extendedEject = 1;
//    public static final int retractedEject = 2;
//    public static final int closeButton = 3;
//    public static final int idle = 4;

    public static final GamepadIndexValues closeButton = GamepadIndexValues.leftBumper;
    public static final GamepadIndexValues forwardButton = GamepadIndexValues.rightBumper;
    public static final GamepadIndexValues backButton = GamepadIndexValues.rightTrigger;
    public static final GamepadIndexValues motorButton = GamepadIndexValues.leftTrigger;
    public static int wrongColor = Color.rgb(200,200,200);
}
