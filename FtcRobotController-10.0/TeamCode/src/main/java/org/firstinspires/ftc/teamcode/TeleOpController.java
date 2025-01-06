package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.EdgeDetection;


@TeleOp
public class TeleOpController extends LinearOpMode {
    private boolean longer = true;
    private int sleep = 80;
    @Override
    public void runOpMode() throws InterruptedException {
        EdgeDetection edgeDetection = new EdgeDetection();
        OuttakeController outtakeController = new OuttakeController(edgeDetection, hardwareMap);
        IntakeController intakeController = new IntakeController(edgeDetection, hardwareMap, outtakeController);
        ArmExtentionController armExtentionController = new ArmExtentionController(edgeDetection, hardwareMap, outtakeController);
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
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();

        if(isStopRequested()) return;

        currentGamepad1.copy(this.gamepad1);
        prevGamepad1.copy(currentGamepad1);

        while (opModeIsActive()) {
            double startStopwatch = System.nanoTime();
            if (gamepad1.triangle) {
                break;
            }
            if (edgeDetection.rising(GamepadIndexValues.share)) {
                longer = !longer;
            }
            if (longer) {
                // Pause the thread for 100 milliseconds as part of the prank
                Thread.sleep(sleep);
                armExtentionController.angleIncrement = 30;
            } else {
                armExtentionController.angleIncrement = 3;
            }
            prevGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);

            drive(imu, frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);

            armExtentionController.updateState();

            intakeController.updateState();

            outtakeController.updateState();

            // Print out loop time
            double loopTime = (System.nanoTime() - startStopwatch) / 1000000;
            if (longer) telemetry.addData("Loop time;", loopTime - sleep);
            else telemetry.addData("Loop time:", loopTime);
            telemetry.addData("risen", outtakeController.risen);
            telemetry.update();
        }

    }

    private void drive(IMU imu, DcMotor frontLeftMotor, DcMotor backLeftMotor, DcMotor frontRightMotor, DcMotor backRightMotor) {
        double ly = -gamepad1.left_stick_y; // Y stick value is reversed
        double lx = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;


        if (gamepad1.options) {
            imu.resetYaw();
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        double rotX = lx * Math.cos(-botHeading) - ly * Math.sin(-botHeading);
        double rotY = lx * Math.sin(-botHeading) + ly * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

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
