package org.firstinspires.ftc.teamcode.HardwareInterface.Sensor;

import android.graphics.Color;

import com.acmerobotics.roadrunner.ftc.LazyImu;
import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Main.Alliance;
import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.HardwareInterface.Gamepad.EdgeDetection;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.ThreeDeadWheelLocalizer;


public class SensorControl {
    public RevHubOrientationOnRobot.LogoFacingDirection logoFacingDirection =
            RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
    public RevHubOrientationOnRobot.UsbFacingDirection usbFacingDirection =
            RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

    private final LimitSwitch[] limitSwitches;
    private final EdgeDetection edgeDetection;
    public final NormalizedColorSensor colorSensor;
    public final LynxI2cColorRangeSensor rangeSensor;
    public int currentColor;
    private int currentRed;
    private int currentGreen;
    private int currentBlue;
    private double currentDistance;
    private LazyImu lazyImu;
    private IMU imu;
    private double angleModifier = 0; //used instead of internal angle reset, this is ugly but I don't want to mod roadrunner

    public SensorControl(HardwareMap hardwareMap, EdgeDetection edgeDetection,  ThreeDeadWheelLocalizer localizer) {
        // Could be added to an array later if more limit switches are introduced
         lazyImu = new LazyImu(hardwareMap, "imu", new RevHubOrientationOnRobot(
                logoFacingDirection, usbFacingDirection));
         imu = lazyImu.get();
         imu.resetYaw();

        limitSwitches = new LimitSwitch[]{
                hardwareMap.get(LimitSwitch.class, "slideLimitSwitch"),
                hardwareMap.get(LimitSwitch.class, "extendoLimitSwitch")
        };
        limitSwitches[0].setMode(LimitSwitch.SwitchConfig.NC);
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "ColorSensor");
        rangeSensor = hardwareMap.get(LynxI2cColorRangeSensor.class, "ColorSensor");
        colorSensor.setGain(2);

        setInitialLocalisationAngle();

        this.edgeDetection = edgeDetection;
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
        double heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        resetLocalizerAngle(heading); //Checks every time, resets only when button pressed
        return heading - angleModifier;
    }

    public void resetLocalizerAngle(double heading) {
        if (edgeDetection.rising(GamepadIndexValues.options)) {
            //localizer.resetEncoders();
            imu.resetYaw();
        }
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