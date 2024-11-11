package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import android.annotation.SuppressLint;
import android.graphics.Color;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;

public class IntakeConstants {
    public static final int yellow = Color.rgb(0, 0, 0);
    @SuppressLint("Range")
    public static final int red = Color.rgb(0, 0, 0);
    public static final int blue = Color.rgb(0, 0, 0);
    private static final boolean isRedAlliance = GlobalVariables.alliance == Alliance.Red;
    public static final int allianceColor = isRedAlliance ? red:blue; //fancy ahh declaration
    public static final int threshold = 5;

    public static final double intakeSpeed = 1;
    public static final double servoSpeed = 1;

    public static final double secureSampleTime = 0;
    public static final double intakePushoutTime = 1;
    public static final double pushToEndTime = 0.6;
    public static final double clawCloseTime = 0.6;

//    public static final int autoClose = 0;
//    public static final int extendedEject = 1;
//    public static final int retractedEject = 2;
//    public static final int closeButton = 3;
//    public static final int idle = 4;

    public static final GamepadIndexValues closeButton = GamepadIndexValues.leftBumper;
    public static final GamepadIndexValues forwardButton = GamepadIndexValues.rightBumper;
    public static final GamepadIndexValues backButton = GamepadIndexValues.rightTrigger;
    public static final GamepadIndexValues motorButton = GamepadIndexValues.leftTrigger;
    @SuppressLint("Range")
    public static int wrongColor = Color.rgb(456,117,148);
}
