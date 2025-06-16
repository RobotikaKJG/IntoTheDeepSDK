package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.EdgeDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@TeleOp
public class WebcamXandYAutoAlign extends LinearOpMode {
    OpenCvWebcam webcam;
    BlueDetectionPipeline pipeline;

    Gamepad currentGamepad1 = new Gamepad();
    Gamepad prevGamepad1 = new Gamepad();

    DcMotor frontLeft, frontRight, backLeft, backRight;
    GoBildaPinpointDriver pinpointDriver;

    final double centerY = 160; // for 320px width
    final double centerX = 160;
    final double kp = 0.002; // Tune this!
    final double tolerance = 5; // Pixels
    private boolean track = false;
    double headingLock = 0;
    final double kHeading = 0.25;
    double headingError = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize motors
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        pinpointDriver = hardwareMap.get(GoBildaPinpointDriver.class, "pinpointIMU");
        pinpointDriver.initialize();
        pinpointDriver.resetPosAndIMU();

        // Setup webcam
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance()
                .createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        pipeline = new BlueDetectionPipeline(320);
        webcam.setPipeline(pipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error", errorCode);
                telemetry.update();
            }
        });

        EdgeDetection edgeDetection = new EdgeDetection();
        Gamepad current = new Gamepad();
        Gamepad previous = new Gamepad();

        waitForStart();

        while (opModeIsActive()) {
            pinpointDriver.update();

            prevGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);

            if (gamepad1.triangle) return;

            if (edgeDetection.rising(GamepadIndexValues.circle)) {
                track = !track;
                if (track) {
                    headingLock = pinpointDriver.getHeading(); // lock heading when tracking starts
                }
            }

            double objectX = pipeline.getObjectX(); // left-right on screen
            double objectY = pipeline.getObjectY(); // top-bottom on screen

            double strafe = 0; // X axis correction
            double forward = 0; // Y axis correction

            // Auto-centering logic (when tracking is enabled)
            if (track) {
                double currentHeading = pinpointDriver.getHeading();
                headingError = currentHeading - headingLock;
                headingError = Math.atan2(Math.sin(headingError), Math.cos(headingError)); // normalize

                double rx = gamepad1.right_stick_x;
                if (Math.abs(rx) < 0.05) {
                    rx += kHeading * headingError;
                }

                // X (strafe) correction
                if (objectX != -1) {
                    double errorX = objectX - centerX;
                    if (Math.abs(errorX) > tolerance) {
                        forward = kp * errorX;
                        forward = Math.max(Math.min(forward, 0.4), -0.4);
                    }
                }

                // Y (forward) correction
                if (objectY != -1) {
                    double errorY = objectY - centerY;
                    if (Math.abs(errorY) > tolerance) {
                        strafe = kp * errorY;
                        strafe = Math.max(Math.min(strafe, 0.4), -0.4);
                    }
                }

                // Apply movement
                double fl = forward + strafe + rx;
                double bl = forward - strafe + rx;
                double fr = forward - strafe - rx;
                double br = forward + strafe - rx;

                double max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(bl), Math.max(Math.abs(fr), Math.abs(br)))));
                frontLeft.setPower(fl / max);
                backLeft.setPower(bl / max);
                frontRight.setPower(fr / max);
                backRight.setPower(br / max);
            } else {
                // Manual driver-oriented control using pinpoint IMU
                double stickY = -gamepad1.left_stick_y;
                double stickX = gamepad1.left_stick_x;

                double heading = -pinpointDriver.getHeading(); // negative for correct field rotation
                double rotatedX = stickX * Math.cos(heading) - stickY * Math.sin(heading);
                double rotatedY = stickX * Math.sin(heading) + stickY * Math.cos(heading);

                forward = rotatedY;
                strafe = rotatedX;
            }

            double rx = gamepad1.right_stick_x; // rotation

            // Apply movement
            double fl = forward + strafe + rx;
            double bl = forward - strafe + rx;
            double fr = forward - strafe - rx;
            double br = forward + strafe - rx;

            double max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(bl), Math.max(Math.abs(fr), Math.abs(br)))));
            frontLeft.setPower(fl / max);
            backLeft.setPower(bl / max);
            frontRight.setPower(fr / max);
            backRight.setPower(br / max);

            telemetry.addData("Tracking", track ? "ON" : "OFF");
            telemetry.addData("Strafe Power", strafe);
            telemetry.addData("Forward Power", forward);
            telemetry.addData("Heading (°)", Math.toDegrees(pinpointDriver.getHeading()));
            telemetry.addData("Heading Error", headingError);
            telemetry.update();
        }

    }
}