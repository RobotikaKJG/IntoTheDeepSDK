package org.firstinspires.ftc.teamcode.HardwareInterface;

import android.graphics.Color;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Enums.Alliance;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.SensorCode.LimitSwitch;


public class SensorControl {

    private final LimitSwitch limitSwitch;
    private final Gamepad gamepad1;
    private final StandardTrackingWheelLocalizer localizer;
    public final ColorSensor colorSensor;

    public SensorControl(HardwareMap hardwareMap, Gamepad gamepad1, StandardTrackingWheelLocalizer localizer) {
        // Could be added to an array later if more limit switches are introduced
        limitSwitch = hardwareMap.get(LimitSwitch.class, "limitSwitch");
        limitSwitch.setMode(LimitSwitch.SwitchConfig.NC);
        colorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");
        this.localizer = localizer;

        setInitialLocalisationAngle();

        this.gamepad1 = gamepad1;
    }

    private void setInitialLocalisationAngle() {
        if (!GlobalVariables.wasAutonomous)
            localizer.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
        else if (GlobalVariables.alliance == Alliance.Red)
            localizer.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(90)));
        else
            localizer.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(-90)));
        GlobalVariables.wasAutonomous = false;
    }

    public double getLocalizerAngle() {
        resetLocalizerAngle(); //Checks every time, resets only when button pressed
        Pose2d currentPose = localizer.getPoseEstimate();
        return currentPose.getHeading();
    }

    public void resetLocalizerAngle() {
        if (gamepad1.options)
            localizer.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
    }

    public boolean isLimitSwitchPressed() {
        return limitSwitch.getIsPressed();
    }

    public boolean isColorMatch(int targetColor, int threshold) {
        // Efficiently read the color values in a single call
        int detectedColor = colorSensor.argb();

        // Extract the RGB components of the detected and target colors
        int detectedRed = colorSensor.red();
        int detectedGreen = colorSensor.green();
        int detectedBlue = colorSensor.blue();

        int targetRed = Color.red(targetColor);
        int targetGreen = Color.green(targetColor);
        int targetBlue = Color.blue(targetColor);

        // Calculate the difference for each color channel
        int redDiff = Math.abs(detectedRed - targetRed);
        int greenDiff = Math.abs(detectedGreen - targetGreen);
        int blueDiff = Math.abs(detectedBlue - targetBlue);

        // Check if the differences are within the threshold for each channel
        return redDiff <= threshold && greenDiff <= threshold && blueDiff <= threshold;
    }
}