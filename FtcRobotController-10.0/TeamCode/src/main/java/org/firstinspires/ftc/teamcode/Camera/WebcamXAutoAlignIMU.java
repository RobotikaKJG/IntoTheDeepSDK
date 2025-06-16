package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.EdgeDetection;
import org.firstinspires.ftc.teamcode.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

@TeleOp
public class WebcamXAutoAlignIMU extends LinearOpMode {
    OpenCvWebcam webcam;
    BlueDetectionPipeline pipeline;

    DcMotor frontLeft, frontRight, backLeft, backRight;
    GoBildaPinpointDriver pinpointDriver;

    final double centerY = 160; // for 320px width
    final double kp = 0.003; // Tune this!
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
            double objectY = pipeline.getObjectY();
            double strafe = 0;
            previous.copy(current);
            current.copy(gamepad1);
            edgeDetection.refreshGamepadIndex(current, previous);

            pinpointDriver.update();

            if (gamepad1.triangle) {
                return;
            }

            if (gamepad1.options) {
                pinpointDriver.resetPosAndIMU();
            }

            if (edgeDetection.rising(GamepadIndexValues.circle)) { // Circle is index 1
                track = !track;
                if (track) {
                    headingLock = pinpointDriver.getHeading(); // Lock current heading
                }
            }

            // Calculate automatic strafe correction
            if (objectY != -1) {
                double error = objectY - centerY;

                if (Math.abs(error) > tolerance) {
                    strafe = kp * error;
                    strafe = Math.max(Math.min(strafe, 0.4), -0.4); // clamp to avoid overdrive
                }
            }

            // Manual control: forward/backward (Y), rotate (RX)
            double y = -gamepad1.left_stick_y; // forward/back
            double rx = gamepad1.right_stick_x;

            if (track) {
                double currentHeading = pinpointDriver.getHeading();
                headingError = currentHeading - headingLock;

                // Normalize heading error to range [-π, π]
                headingError = Math.atan2(Math.sin(headingError), Math.cos(headingError));

                // Add correction only if user isn’t manually turning
                if (Math.abs(rx) < 0.05) {
                    rx += kHeading * headingError;
                }

                // Forward = left stick Y, strafe from vision
                double forward = y;

                // Combine movement: manual forward + auto strafe + corrected rotate
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
                // Driver-oriented (field-centric) control
                double stickY = y; // forward/backward
                double stickX = gamepad1.left_stick_x;  // left/right

                double heading = pinpointDriver.getHeading();

                // Rotate joystick vector by negative heading
                double rotatedX = stickX * Math.cos(-heading) - stickY * Math.sin(-heading);
                double rotatedY = stickX * Math.sin(-heading) + stickY * Math.cos(-heading);

                double fl = rotatedY + rotatedX + rx;
                double bl = rotatedY - rotatedX + rx;
                double fr = rotatedY - rotatedX - rx;
                double br = rotatedY + rotatedX - rx;

                double max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(bl), Math.max(Math.abs(fr), Math.abs(br)))));
                frontLeft.setPower(fl / max);
                backLeft.setPower(bl / max);
                frontRight.setPower(fr / max);
                backRight.setPower(br / max);
            }

            telemetry.addData("ObjectY", objectY);
            telemetry.addData("Strafe Power", strafe);
            telemetry.addData("Heading (°)", Math.toDegrees(pinpointDriver.getHeading()));
            telemetry.addData("HeadingLock", headingLock);
            telemetry.addData("Heading error", headingError);
            telemetry.update();
        }
    }
}