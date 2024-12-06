package org.firstinspires.ftc.teamcode.DriveTrain.TestOpModes;

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
import org.firstinspires.ftc.teamcode.EdgeDetection;

@TeleOp(name = "Drive Base")
public class DriveBase extends LinearOpMode {

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

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        currentGamepad1.copy(this.gamepad1);
        prevGamepad1.copy(currentGamepad1);

        while (opModeIsActive()) {
            double startStopwatch = System.nanoTime();
            prevGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);

            drive(imu, frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);

            intakeController.updateState();

            double loopTime = (System.nanoTime() - startStopwatch) / 1000000;
            telemetry.addData("Loop time:", loopTime);

            // Output the robot's current angle
            double botHeadingRadians = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            double botHeadingDegrees = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
            telemetry.addData("Robot Angle (Radians):", botHeadingRadians);
            telemetry.addData("Robot Angle (Degrees):", botHeadingDegrees);

            telemetry.update();
        }
    }

    /**
     * Drive function for driver-oriented control based on IMU yaw.
     */
    private void drive(IMU imu, DcMotor frontLeftMotor, DcMotor backLeftMotor, DcMotor frontRightMotor, DcMotor backRightMotor) {
        double y = -gamepad1.left_stick_y; // Forward/backward
        double x = gamepad1.left_stick_x;  // Left/right
        double rx = gamepad1.right_stick_x; // Rotation

        if (gamepad1.options) {
            imu.resetYaw();
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Adjust input based on the driver's perspective
        double adjustedX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
        double adjustedY = x * Math.sin(botHeading) + y * Math.cos(botHeading);

        // Counteract imperfect strafing
        adjustedX *= 1.1;

        double denominator = Math.max(Math.abs(adjustedY) + Math.abs(adjustedX) + Math.abs(rx), 1);
        double frontLeftTargetPower = (adjustedY + adjustedX + rx) / denominator;
        double backLeftTargetPower = (adjustedY - adjustedX + rx) / denominator;
        double frontRightTargetPower = (adjustedY - adjustedX - rx) / denominator;
        double backRightTargetPower = (adjustedY + adjustedX - rx) / denominator;

        // Set motor power
        frontLeftMotor.setPower(frontLeftTargetPower);
        backLeftMotor.setPower(backLeftTargetPower);
        frontRightMotor.setPower(frontRightTargetPower);
        backRightMotor.setPower(backRightTargetPower);

        // Telemetry for debugging
        telemetry.addData("Front Left Power", frontLeftMotor.getPower());
        telemetry.addData("Back Left Power", backLeftMotor.getPower());
        telemetry.addData("Front Right Power", frontRightMotor.getPower());
        telemetry.addData("Back Right Power", backRightMotor.getPower());
        telemetry.update();
    }
}
