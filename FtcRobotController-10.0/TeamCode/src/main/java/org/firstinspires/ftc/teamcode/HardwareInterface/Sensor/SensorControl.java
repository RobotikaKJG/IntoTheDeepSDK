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
    public int currentRed;
    public int currentGreen;
    public int currentBlue;
    private double currentDistance;

    public SensorControl(HardwareMap hardwareMap, EdgeDetection edgeDetection,  StandardTrackingWheelLocalizer localizer) {
        limitSwitches = getLimitSwitches(hardwareMap);

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "ColorSensor");
        rangeSensor = hardwareMap.get(LynxI2cColorRangeSensor.class, "ColorSensor");
        colorSensor.setGain(3);//2);

        this.localizer = localizer;
        setInitialLocalisationAngle();

        this.edgeDetection = edgeDetection;
    }

    private LimitSwitch[] getLimitSwitches(HardwareMap hardwareMap) {
        final LimitSwitch[] limitSwitches;
        limitSwitches = new LimitSwitch[]{
                hardwareMap.get(LimitSwitch.class, "leftSlideLimitSwitch"),
                hardwareMap.get(LimitSwitch.class, "rightSlideLimitSwitch"),
                hardwareMap.get(LimitSwitch.class, "extendoLimitSwitch")
        };

        limitSwitches[0].setMode(LimitSwitch.SwitchConfig.NC);
        limitSwitches[1].setMode(LimitSwitch.SwitchConfig.NC);
        limitSwitches[2].setMode(LimitSwitch.SwitchConfig.NC);
        return limitSwitches;
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
            case slideLeft:
                return limitSwitches[0].getIsPressed();
            case slideRight:
                return limitSwitches[1].getIsPressed();
            case extendo:
                return limitSwitches[2].getIsPressed();
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

    public void resetColor(){
        currentColor = 0;
        currentRed = 0;
        currentGreen = 0;
        currentBlue = 0;
    }

    public void updateDistance(){
        currentDistance = rangeSensor.getDistance(DistanceUnit.MM);
    }

    public void resetDistance(){
        currentDistance = 100;
    }

    public boolean isRed(){
        //return currentGreen < 5 && currentRed > 7 || (currentBlue == 2 && currentGreen == 2 && currentRed == 5);
        return currentGreen < 6 && currentRed > 6;
    }

    public boolean isYellow(){
        return currentGreen > 7;
    }

    public boolean isBlue(){
//        return (currentRed < 5 && currentBlue > 3 && currentGreen < 8) || ( currentRed == 1 && currentBlue == 3 && currentGreen < 4);
        return currentRed <= 4 && currentBlue > 5;
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