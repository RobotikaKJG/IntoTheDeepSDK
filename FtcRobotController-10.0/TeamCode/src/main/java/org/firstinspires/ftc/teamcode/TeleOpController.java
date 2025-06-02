package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;


@TeleOp
public class TeleOpController extends LinearOpMode {
    public static boolean isUp = false;
    public static boolean wasDown = false;
    public static boolean sample = false;

    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;

    GoBildaPinpointDriver pinpointDriver;

    @Override
    public void runOpMode() throws InterruptedException {

        // Optional: Initialize other necessary controllers
        GoBildaPinpointDriver pinpointDriver = hardwareMap.get(GoBildaPinpointDriver.class, "pinpointIMU");
        pinpointDriver.initialize();
        pinpointDriver.resetPosAndIMU();

        // Initialize other controllers here...
        EdgeDetection edgeDetection = new EdgeDetection();
        OuttakeController outtakeController = new OuttakeController(edgeDetection, hardwareMap);
        IntakeController intakeController = new IntakeController(edgeDetection, hardwareMap);
//        ArmExtentionController armExtentionController = new ArmExtentionController(edgeDetection, intakeController, hardwareMap, outtakeController);
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad prevGamepad1 = new Gamepad();

        // Define the motors
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRight");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        currentGamepad1.copy(this.gamepad1);
        prevGamepad1.copy(currentGamepad1);

        while (opModeIsActive()) {
            double startStopwatch = System.nanoTime();

            // Update Pinpoint data
            pinpointDriver.update();

            if (gamepad1.options) {
                pinpointDriver.resetPosAndIMU();
            }

            // Exit condition
            if (gamepad1.triangle) {
                break;
            }

            double drivePower = -gamepad1.left_stick_y; // Forward/Backward
            double strafePower = -gamepad1.left_stick_x; // Strafing
            double turnPower = -gamepad1.right_stick_x; // Rotation

            // Controller updates
            prevGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);

            double y = -gamepad1.left_stick_y; // Forward/backward
            double x = gamepad1.left_stick_x;  // Strafe
            double rx = gamepad1.right_stick_x; // Rotation

            double botHeading = pinpointDriver.getHeading();

            // Field-centric transformation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1; // Compensate for imperfect strafing

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

//            armExtentionController.updateState();
            intakeController.updateState();
            outtakeController.updateState();

//            isUp = intakeController.isUp;
//            wasDown = intakeController.wasDown;
//            sample = intakeController.sample;

            // Print out loop time
            double loopTime = (System.nanoTime() - startStopwatch) / 1000000;

            telemetry.addData("Yaw (Degrees)", Math.toDegrees(pinpointDriver.getHeading()));
            telemetry.update();
        }

    }

}