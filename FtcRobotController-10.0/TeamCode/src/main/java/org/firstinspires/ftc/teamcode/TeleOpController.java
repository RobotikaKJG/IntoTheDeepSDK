package org.firstinspires.ftc.teamcode;

import android.graphics.ColorSpace;
import android.os.Build;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.IntakeController;
import org.firstinspires.ftc.teamcode.ServoControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;


@TeleOp
public class TeleOpController extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ServoControl servoControl = new ServoControl(hardwareMap);
        EdgeDetection edgeDetection = new EdgeDetection();
        IntakeController intakeController = new IntakeController(servoControl, edgeDetection);
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad prevGamepad1 = new Gamepad();

        // Define the motors
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        IMU imu = hardwareMap.get(IMU.class, "imu");


        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        waitForStart();

        if(isStopRequested()) return;

        currentGamepad1.copy(this.gamepad1);
        prevGamepad1.copy(currentGamepad1);

        while (opModeIsActive()) {
            double startStopwatch = System.nanoTime();
            prevGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);

            drive(imu, frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);

            intakeController.updateState();

            // Print out loop time
            double loopTime = (System.nanoTime() - startStopwatch) / 1000000;
            telemetry.addData("Loop time:", loopTime);
            telemetry.update();
        }

    }

    private void drive(IMU imu, DcMotor frontLeftMotor, DcMotor backLeftMotor, DcMotor frontRightMotor, DcMotor backRightMotor) {
        double ly = -gamepad1.left_stick_y;
        double lx = gamepad1.left_stick_x * 1.1;
        double rx = gamepad1.right_stick_x;


        if (gamepad1.options) {
            imu.resetYaw();
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = lx * Math.cos(-botHeading) - ly * Math.sin(-botHeading);
        double rotY = lx * Math.sin(-botHeading) + ly * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }
}
