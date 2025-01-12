package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;


@TeleOp
public class TeleOpController extends LinearOpMode {
    private boolean longer = true;
    private int sleep = 80;
    public static boolean isUp = false;
    public static boolean wasDown = false;
    public static boolean sample = false;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the Pinpoint Odometry Computer
        GoBildaPinpointDriver pinpointDriver = hardwareMap.get(GoBildaPinpointDriver.class, "pinpointIMU");
        pinpointDriver.initialize();

        // Optional: Reset the position and calibrate IMU at start
        pinpointDriver.resetPosAndIMU();

        // Other initialization logic
        EdgeDetection edgeDetection = new EdgeDetection();
        OuttakeController outtakeController = new OuttakeController(edgeDetection, hardwareMap);
        IntakeController intakeController = new IntakeController(edgeDetection, hardwareMap, outtakeController);
        ArmExtentionController armExtentionController = new ArmExtentionController(edgeDetection, intakeController, hardwareMap, outtakeController);
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad prevGamepad1 = new Gamepad();

        // Define the motors
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        currentGamepad1.copy(this.gamepad1);
        prevGamepad1.copy(currentGamepad1);

        while (opModeIsActive()) {
            double startStopwatch = System.nanoTime();

            // Update Pinpoint data
            pinpointDriver.update();

            if (gamepad1.triangle) {
                break;
            }
            if (edgeDetection.rising(GamepadIndexValues.share)) {
                longer = !longer;
            }
            if (longer) {
                Thread.sleep(sleep);
                armExtentionController.angleIncrement = 30;
            } else {
                armExtentionController.angleIncrement = 6;
            }

            prevGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);

            // Use Pinpoint yaw for driving
            drive(pinpointDriver, frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);

            armExtentionController.updateState();
            intakeController.updateState();
            outtakeController.updateState();

            isUp = intakeController.isUp;
            wasDown = intakeController.wasDown;
            sample = intakeController.sample;

            // Print out loop time
            double loopTime = (System.nanoTime() - startStopwatch) / 1000000;
            if (longer) telemetry.addData("Loop time;", loopTime - sleep);
            else telemetry.addData("Loop time:", loopTime);
            telemetry.addData("isup", intakeController.isUp);
            telemetry.addData("open", intakeController.open);
            telemetry.addData("sample", intakeController.sample);
            telemetry.addData("square", outtakeController.square);
            telemetry.addData("angle:", armExtentionController.currentAngle);

            telemetry.addData("Yaw (Degrees)", Math.toDegrees(pinpointDriver.getHeading()));
            telemetry.update();
        }
    }

    private void drive(GoBildaPinpointDriver pinpointDriver, DcMotor frontLeftMotor, DcMotor backLeftMotor, DcMotor frontRightMotor, DcMotor backRightMotor) {
        double ly = -gamepad1.left_stick_y; // Y stick value is reversed
        double lx = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        if (gamepad1.options) {
            pinpointDriver.resetPosAndIMU();
        }

        // Use Pinpoint's heading instead of the IMU
        double botHeading = pinpointDriver.getHeading();

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
