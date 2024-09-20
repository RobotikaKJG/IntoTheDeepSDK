package org.firstinspires.ftc.teamcode.HardwareInterface;

import com.acmerobotics.roadrunner.geometry.Pose2d;
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

    public SensorControl(HardwareMap hardwareMap, Gamepad gamepad1, StandardTrackingWheelLocalizer localizer) {
        // Could be added to an array later if more limit switches are introduced
        limitSwitch = hardwareMap.get(LimitSwitch.class, "limitSwitch");
        limitSwitch.setMode(LimitSwitch.SwitchConfig.NC);
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
}