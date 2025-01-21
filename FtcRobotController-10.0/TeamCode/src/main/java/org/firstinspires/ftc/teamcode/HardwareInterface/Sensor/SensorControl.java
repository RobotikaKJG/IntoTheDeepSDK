package org.firstinspires.ftc.teamcode.HardwareInterface.Sensor;

import android.graphics.Color;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Main.Alliance;
import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.EdgeDetection;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.StandardTrackingWheelLocalizer;


public class SensorControl {

    private final LimitSwitch[] limitSwitches;
    private final EdgeDetection edgeDetection;
    private final StandardTrackingWheelLocalizer localizer;
    public final NormalizedColorSensor colorSensor;
    public final LynxI2cColorRangeSensor rangeSensor;
    public int currentColor;
    private int currentRed;
    private int currentGreen;
    private int currentBlue;
    private double currentDistance;


        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "ColorSensor");
        rangeSensor = hardwareMap.get(LynxI2cColorRangeSensor.class, "ColorSensor");
        colorSensor.setGain(2);

        setInitialLocalisationAngle();

        this.edgeDetection = edgeDetection;
    }

    private void setInitialLocalisationAngle() {
        if (!GlobalVariables.wasAutonomous)
            localizer.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
        else {
            GlobalVariables.wasAutonomous = false;
            localizer.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(-45)));
        }
    }

    public double getLocalizerAngle() {
        resetLocalizerAngle(); //Checks every time, resets only when button pressed
        Pose2d currentPose = localizer.getPoseEstimate();
        return currentPose.getHeading();
    }

    public void resetLocalizerAngle() {
        if (edgeDetection.rising(GamepadIndexValues.options))
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

    public void updateColor(){
        currentColor = colorSensor.getNormalizedColors().toColor();
        currentRed = Color.red(currentColor);
        currentGreen = Color.green(currentColor);
        currentBlue = Color.blue(currentColor);
    }

    public void updateDistance(){
        currentDistance = rangeSensor.getDistance(DistanceUnit.MM);
    }

    public boolean isRed(){
        return currentGreen < 5 && currentRed > 7 || currentColor == Color.rgb(5,2,2);
    }
    public boolean isYellow(){
        return currentGreen > 6;
    }
    public boolean isBlue(){
        return currentRed < 5 && currentBlue > 2 && currentGreen < 8;
    }

    public double getDistance(){
        return currentDistance;
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