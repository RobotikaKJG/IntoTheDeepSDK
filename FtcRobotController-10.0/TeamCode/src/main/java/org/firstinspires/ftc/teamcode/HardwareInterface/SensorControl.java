package org.firstinspires.ftc.teamcode.HardwareInterface;

import android.graphics.Color;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.Enums.Alliance;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.SensorCode.LimitSwitch;


public class SensorControl {

    private final LimitSwitch[] limitSwitches;
    private final Gamepad gamepad1;
    private final StandardTrackingWheelLocalizer localizer;
    public final NormalizedColorSensor colorSensor;

    public SensorControl(HardwareMap hardwareMap, Gamepad gamepad1, StandardTrackingWheelLocalizer localizer) {
        // Could be added to an array later if more limit switches are introduced
        limitSwitches = new LimitSwitch[]{
                hardwareMap.get(LimitSwitch.class, "slideLimitSwitch"),
                hardwareMap.get(LimitSwitch.class, "extendoLimitSwitch")
        };
        limitSwitches[0].setMode(LimitSwitch.SwitchConfig.NC);
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "ColorSensor");
        colorSensor.setGain(10);
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

    public boolean isLimitSwitchPressed(LimitSwitches state) {
        switch (state) {
            case slide:
                return limitSwitches[0].getIsPressed();
            case extendo:
                return limitSwitches[1].getIsPressed();
            default:
                return false; // Or throw an exception
        }
    }

    public boolean isColorMatch(int targetColor, int threshold) {

        NormalizedRGBA currentColor = colorSensor.getNormalizedColors();
        int convertedColor = currentColor.toColor();

        int detectedRed = Color.red(convertedColor);
        int detectedGreen = Color.green(convertedColor);
        int detectedBlue = Color.blue(convertedColor);

        int targetRed = Color.red(targetColor);
        int targetGreen = Color.green(targetColor);
        int targetBlue = Color.blue(targetColor);

        int redDiff = Math.abs(detectedRed - targetRed);
        int greenDiff = Math.abs(detectedGreen - targetGreen);
        int blueDiff = Math.abs(detectedBlue - targetBlue);

        return redDiff <= threshold && greenDiff <= threshold && blueDiff <= threshold;
    }
}