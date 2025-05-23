package org.firstinspires.ftc.teamcode.Subsystems.Drivebase;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;


public class Drivebase {

    private final Gamepad gamepad1;
    private final MotorControl motorControl;
    private final SensorControl sensorControl;
    private boolean isDriverOriented = true;

    public Drivebase(Gamepad gamepad1, MotorControl motorControl, SensorControl sensorControl) {
        this.gamepad1 = gamepad1;
        this.motorControl = motorControl;
        this.sensorControl = sensorControl;
    }

    public void gamepadDrive(double maxSpeed) {
        double y = -gamepad1.left_stick_y;// * yGain;
        double x = -gamepad1.left_stick_x;// * xGain;
        double rotation = gamepad1.right_stick_x;// * rotationGain;
        robotOrientedGamepadDrive(y, x, rotation, maxSpeed);
    }

    public void driverOrientedGamepadDrive(double maxSpeed) {
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rotation = gamepad1.right_stick_x;
        driverOrientedGamepadDrive(y, x, rotation, maxSpeed);
    }

    private void robotOrientedGamepadDrive(double y, double x, double rotation, double maxSpeed) {

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rotation), 1);
        motorControl.setMotorSpeed(MotorConstants.allDrive, y);

        motorControl.addMotorSpeed(MotorConstants.leftDrive, rotation);
        motorControl.addMotorSpeed(MotorConstants.rightDrive, -rotation);

        motorControl.addMotorSpeed(MotorConstants.frontRightBackLeft, x);
        motorControl.addMotorSpeed(MotorConstants.frontLeftBackRight, -x);

        motorControl.divideMotorSpeed(MotorConstants.allDrive, denominator);
        motorControl.multiplyMotorSpeed(MotorConstants.allDrive, maxSpeed);
    }

    private void driverOrientedGamepadDrive(double y, double x, double rotation, double maxSpeed) {
        double botHeading = sensorControl.getLocalizerAngle();
        double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
        double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rotation), 1);

        motorControl.setMotorSpeed(MotorConstants.allDrive, -rotY);

        motorControl.addMotorSpeed(MotorConstants.leftDrive, rotation);
        motorControl.addMotorSpeed(MotorConstants.rightDrive, -rotation);

        motorControl.addMotorSpeed(MotorConstants.frontRightBackLeft, -rotX);
        motorControl.addMotorSpeed(MotorConstants.frontLeftBackRight, rotX);

        motorControl.divideMotorSpeed(MotorConstants.allDrive, denominator);
        motorControl.multiplyMotorSpeed(MotorConstants.allDrive, maxSpeed);
    }

    public void switchDrivingMode() {
        isDriverOriented = !isDriverOriented;
    }

    public void drive(double maxSpeed) {
        if (isDriverOriented)
            driverOrientedGamepadDrive(maxSpeed);
        else
            gamepadDrive(maxSpeed);
    }
}