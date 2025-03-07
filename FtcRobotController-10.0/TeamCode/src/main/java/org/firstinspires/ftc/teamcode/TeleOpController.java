package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import com.acmerobotics.roadrunner.geometry.Pose2d;

@TeleOp
public class TeleOpController extends LinearOpMode {
    private boolean longer = true;
    private int sleep = 80;
    public static boolean isUp = false;
    public static boolean wasDown = false;
    public static boolean sample = false;

    private SampleMecanumDrive driveBase;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the SampleMecanumDrive (RoadRunner Drive)
        driveBase = new SampleMecanumDrive(hardwareMap);

        // Optional: Initialize other necessary controllers
        GoBildaPinpointDriver pinpointDriver = hardwareMap.get(GoBildaPinpointDriver.class, "pinpointIMU");
        pinpointDriver.initialize();
        pinpointDriver.resetPosAndIMU();

        // Initialize other controllers here...
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

        currentGamepad1.copy(this.gamepad1);
        prevGamepad1.copy(currentGamepad1);

        while (opModeIsActive()) {
            double startStopwatch = System.nanoTime();

            // Update Pinpoint data
            pinpointDriver.update();

            // Exit condition
            if (gamepad1.triangle) {
                break;
            }

            // Toggle movement speed
            if (edgeDetection.rising(GamepadIndexValues.share)) {
                longer = !longer;
            }
            if (longer) {
                Thread.sleep(sleep);
                armExtentionController.angleIncrement = 30;
            } else {
                armExtentionController.angleIncrement = 6;
            }

            // Handle motion profiling
            Pose2d currentPose = driveBase.getPoseEstimate();

            double drivePower = -gamepad1.left_stick_y; // Forward/Backward
            double strafePower = -gamepad1.left_stick_x; // Strafing
            double turnPower = -gamepad1.right_stick_x; // Rotation

            if (Math.abs(drivePower) > 0.1 || Math.abs(strafePower) > 0.1 || Math.abs(turnPower) > 0.1) {
                // Calculate the target pose
                Pose2d targetPose = new Pose2d(
                        currentPose.getX() + drivePower * 10,  // Adjust distance factor
                        currentPose.getY() + strafePower * 10,
                        currentPose.getHeading() + turnPower * Math.toRadians(30)
                );

                // Create a trajectory to the target position
                Trajectory trajectory = driveBase.trajectoryBuilder(currentPose)
                        .lineToLinearHeading(targetPose)
                        .build();

                // Follow the trajectory
                driveBase.followTrajectoryAsync(trajectory);
            }

            // Update Road Runner's pose estimation
            driveBase.update();

            // Controller updates
            prevGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);

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

            telemetry.addData("Yaw (Degrees)", Math.toDegrees(pinpointDriver.getHeading()));
            telemetry.update();
        }

    }
}
