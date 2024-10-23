package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import android.graphics.Color;

import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;

public class IntakeConstants {
    public static final int slideExtensionStep = 370;
    public static final int targetColor = Color.rgb(0, 0, 0);
    public static final int threshold = 5;
    public static final double intakePushoutTime = 0.5;

    public static final GamepadIndexValues closeButton = GamepadIndexValues.leftBumper;
    public static final GamepadIndexValues forwardButton = GamepadIndexValues.rightBumper;
    public static final GamepadIndexValues backButton = GamepadIndexValues.rightTrigger;
    public static final GamepadIndexValues motorButton = GamepadIndexValues.leftTrigger;
}
