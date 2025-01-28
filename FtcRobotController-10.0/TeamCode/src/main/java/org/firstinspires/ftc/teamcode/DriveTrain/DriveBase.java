package org.firstinspires.ftc.teamcode.DriveTrain;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class DriveBase {

    private DcMotor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private IMU imu;

    public DriveBase(LinearOpMode opMode) {
        // Hardware initialization
        frontLeftMotor = opMode.hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = opMode.hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor = opMode.hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = opMode.hardwareMap.dcMotor.get("backRightMotor");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        imu = opMode.hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);
    }

    public void updateDrive(Gamepad gamepad1, LinearOpMode opMode) {
        double y = -gamepad1.left_stick_y; // Y stick value is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        if (gamepad1.options) {
            imu.resetYaw();
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftTargetPower = (rotY + rotX + rx) / denominator;
        double backLeftTargetPower = (rotY - rotX + rx) / denominator;
        double frontRightTargetPower = (rotY - rotX - rx) / denominator;
        double backRightTargetPower = (rotY + rotX - rx) / denominator;

        frontLeftMotor.setPower(frontLeftTargetPower);
        backLeftMotor.setPower(backLeftTargetPower);
        frontRightMotor.setPower(frontRightTargetPower);
        backRightMotor.setPower(backRightTargetPower);

        opMode.telemetry.update();
    }

    public void setMotorPowers(double frontLeft, double backLeft, double frontRight, double backRight) {
        frontLeftMotor.setPower(frontLeft);
        backLeftMotor.setPower(backLeft);
        frontRightMotor.setPower(frontRight);
        backRightMotor.setPower(backRight);
    }

}
