package org.firstinspires.ftc.teamcode.HardwareInterface;

import android.graphics.Color;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;


import org.firstinspires.ftc.teamcode.Enums.Alliance;
import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.ThreeDeadWheelLocalizer;
import org.firstinspires.ftc.teamcode.SensorCode.LimitSwitch;


public class SensorControl {

    private final LimitSwitch[] limitSwitches;
    private final EdgeDetection edgeDetection;
    private final ThreeDeadWheelLocalizer localizer;
    public final NormalizedColorSensor colorSensor;
    public int currentColor;
    private int currentRed;
    private int currentGreen;
    private int currentBlue;
    private double angleModifier = 0; //used instead of internal angle reset, this is ugly but I don't want to mod roadrunner
    private Pose2d pose = new Pose2d(0, 0, 0);

    public SensorControl(HardwareMap hardwareMap, EdgeDetection edgeDetection,  ThreeDeadWheelLocalizer localizer) {
        // Could be added to an array later if more limit switches are introduced
        limitSwitches = new LimitSwitch[]{
                hardwareMap.get(LimitSwitch.class, "slideLimitSwitch"),
                hardwareMap.get(LimitSwitch.class, "extendoLimitSwitch")
        };
        limitSwitches[0].setMode(LimitSwitch.SwitchConfig.NC);
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "ColorSensor");
        colorSensor.setGain(2);
        this.localizer = localizer;

        setInitialLocalisationAngle();

        this.edgeDetection = edgeDetection;;
    }

    private void setInitialLocalisationAngle() {
        if (!GlobalVariables.wasAutonomous)
            angleModifier = 0;
        else {
            GlobalVariables.wasAutonomous = false;
            angleModifier = Math.toRadians(-45);
        }
    }

    public double getLocalizerAngle() {
        resetLocalizerAngle(); //Checks every time, resets only when button pressed
        Twist2dDual<Time> twist = localizer.update();
        Pose2d pose = new Pose2d(0, 0, 0);
        pose = pose.plus(twist.value());
        return pose.heading.toDouble() - angleModifier;
    }

    public void resetLocalizerAngle() {
        if (edgeDetection.rising(GamepadIndexValues.options))
            angleModifier = pose.heading.toDouble();
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

    public void updateColor(){
        currentColor = colorSensor.getNormalizedColors().toColor();
        currentRed = Color.red(currentColor);
        currentGreen = Color.green(currentColor);
        currentBlue = Color.blue(currentColor);
    }

    public boolean isColorMatch(int targetColor, int threshold) {
        int detectedRed = Color.red(currentColor);
        int detectedGreen = Color.green(currentColor);
        int detectedBlue = Color.blue(currentColor);

        int targetRed = Color.red(targetColor);
        int targetGreen = Color.green(targetColor);
        int targetBlue = Color.blue(targetColor);

        int redDiff = Math.abs(detectedRed - targetRed);
        int greenDiff = Math.abs(detectedGreen - targetGreen);
        int blueDiff = Math.abs(detectedBlue - targetBlue);

        return redDiff <= threshold && greenDiff <= threshold && blueDiff <= threshold;
    }

    public boolean isRed(){
        if(currentGreen < 5 && currentRed >5) return true;
        return false;
    }
    public boolean isYellow(){
        if(currentGreen > 5) return true;
        return  false;
    }
    public boolean isBlue(){
        if(currentRed == 2 && currentBlue > 2 && currentGreen < 5) return true;
        return false;
    }

    public boolean isAllianceColor(){
        if(GlobalVariables.alliance == Alliance.Red)
            return isRed();
        return isBlue();
    }

    public boolean isOtherAllianceColor(){
        if(GlobalVariables.alliance == Alliance.Red)
            return isBlue();
        return isRed();
    }
}