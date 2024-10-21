package org.firstinspires.ftc.teamcode.DriveTrain;

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

import org.firstinspires.ftc.teamcode.DriveTrain.MotorSpeed.TractionControl;

@TeleOp(name = "Drive Base with Adjustable Traction Control")
public class DriveBase extends LinearOpMode {

    private boolean tcEnabled = true;
    private boolean previousButtonState = false;

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

        // Create an instance of TractionControl
        TractionControl tractionControl = new TractionControl();

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

            // Toggle traction control when the "O" (circle) button is pressed
            if (gamepad1.circle && !previousButtonState) {
                tcEnabled = !tcEnabled;
            }
            previousButtonState = gamepad1.circle;

            // Adjust the acceleration rate using D-pad
            if (gamepad1.dpad_up) {
                tractionControl.adjustAccelerationRate(true);  // Increase acceleration rate
            } else if (gamepad1.dpad_down) {
                tractionControl.adjustAccelerationRate(false);  // Decrease acceleration rate
            }

            drive(tractionControl, imu, frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);

            intakeController.updateState();

            double loopTime = (System.nanoTime() - startStopwatch) / 1000000;
            telemetry.addData("Loop time:", loopTime);
            telemetry.addData("Traction Control Enabled:", tcEnabled ? "Yes" : "No");
            telemetry.addData("Current Acceleration Rate:", tractionControl.getAccelerationRate());
            telemetry.update();
        }
    }

    /**
     * Drive function with traction control (TC).
     */
    private void drive(TractionControl tractionControl, IMU imu, DcMotor frontLeftMotor, DcMotor backLeftMotor, DcMotor frontRightMotor, DcMotor backRightMotor) {
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

        // Apply the TC system based on the toggle state
        if (tcEnabled) {
            // Gradually adjust each motor's power to the target power with TC
            frontLeftMotor.setPower(tractionControl.gradualAcceleration(frontLeftMotor.getPower(), frontLeftTargetPower, this));
            backLeftMotor.setPower(tractionControl.gradualAcceleration(backLeftMotor.getPower(), backLeftTargetPower, this));
            frontRightMotor.setPower(tractionControl.gradualAcceleration(frontRightMotor.getPower(), frontRightTargetPower, this));
            backRightMotor.setPower(tractionControl.gradualAcceleration(backRightMotor.getPower(), backRightTargetPower, this));
        } else {
            // Directly set the motor power without TC
            frontLeftMotor.setPower(frontLeftTargetPower);
            backLeftMotor.setPower(backLeftTargetPower);
            frontRightMotor.setPower(frontRightTargetPower);
            backRightMotor.setPower(backRightTargetPower);
        }

        // Telemetry for debugging
        telemetry.addData("Front Left Power", frontLeftMotor.getPower());
        telemetry.addData("Back Left Power", backLeftMotor.getPower());
        telemetry.addData("Front Right Power", frontRightMotor.getPower());
        telemetry.addData("Back Right Power", backRightMotor.getPower());
        telemetry.update();
    }
}
