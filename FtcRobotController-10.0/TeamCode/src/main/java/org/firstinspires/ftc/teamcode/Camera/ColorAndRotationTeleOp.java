package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.EdgeDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@TeleOp
public class ColorAndRotationTeleOp extends LinearOpMode {
    OpenCvWebcam webcam;
    ColorAndRotationPipeline pipeline;

    Gamepad currentGamepad1 = new Gamepad();
    Gamepad prevGamepad1 = new Gamepad();

    DcMotor frontLeft, frontRight, backLeft, backRight;
    GoBildaPinpointDriver pinpointDriver;
    Servo rotateServo;

    final double centerY = 120;
    final double centerX = 160;
    final double kp = 0.002;
    final double tolerance = 5;
    private boolean track = false;
    double headingLock = 0;
    final double kHeading = 0.25;
    double headingError = 0;

    int color = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        rotateServo = hardwareMap.servo.get("rotateServo");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        pinpointDriver = hardwareMap.get(GoBildaPinpointDriver.class, "pinpointIMU");
        pinpointDriver.initialize();
        pinpointDriver.resetPosAndIMU();

        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance()
                .createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        pipeline = new ColorAndRotationPipeline();
        pipeline.setTargetColor(ColorAndRotationPipeline.TargetColor.RED);
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

        waitForStart();

        while (opModeIsActive()) {
            pinpointDriver.update();

            prevGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            edgeDetection.refreshGamepadIndex(currentGamepad1, prevGamepad1);

            if (gamepad1.triangle) return;

            if (edgeDetection.rising(GamepadIndexValues.circle)) {
                track = !track;
                if (track) headingLock = pinpointDriver.getHeading();
            }

            if (edgeDetection.rising(GamepadIndexValues.dpadUp)) {
                color = (color + 1) % 3;
                switch (color) {
                    case 0: pipeline.setTargetColor(ColorAndRotationPipeline.TargetColor.RED); break;
                    case 1: pipeline.setTargetColor(ColorAndRotationPipeline.TargetColor.BLUE); break;
                    case 2: pipeline.setTargetColor(ColorAndRotationPipeline.TargetColor.YELLOW); break;
                }
            }

            double objectX = pipeline.getObjectX();
            double objectY = pipeline.getObjectY();
            int angle = pipeline.getRotationAngle();

            double strafe = 0, forward = 0;
            double rx = gamepad1.right_stick_x;

            if (track) {
                // turn servo to match the rotation of the sample
                double servoPosition = 1.0 - (angle / 270.0);
                rotateServo.setPosition(Math.max(0.0, Math.min(1.0, servoPosition)));

                double currentHeading = pinpointDriver.getHeading();
                headingError = Math.atan2(Math.sin(currentHeading - headingLock), Math.cos(currentHeading - headingLock));

                if (Math.abs(rx) < 0.05) rx += kHeading * headingError;

                if (objectX != -1) {
                    double errorX = objectX - centerX;
                    if (Math.abs(errorX) > tolerance) {
                        forward = kp * errorX;
                        forward = Math.max(Math.min(forward, 0.4), -0.4);
                    }
                }

                if (objectY != -1) {
                    double errorY = objectY - centerY;
                    if (Math.abs(errorY) > tolerance) {
                        strafe = kp * errorY;
                        strafe = Math.max(Math.min(strafe, 0.4), -0.4);
                    }
                }
            } else {
                rotateServo.setPosition(0.7);
                double stickY = -gamepad1.left_stick_y;
                double stickX = gamepad1.left_stick_x;

                double heading = -pinpointDriver.getHeading();
                double rotatedX = stickX * Math.cos(heading) - stickY * Math.sin(heading);
                double rotatedY = stickX * Math.sin(heading) + stickY * Math.cos(heading);

                forward = rotatedY;
                strafe = rotatedX;
            }

            double fl = forward + strafe + rx;
            double bl = forward - strafe + rx;
            double fr = forward - strafe - rx;
            double br = forward + strafe - rx;

            double max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(bl), Math.max(Math.abs(fr), Math.abs(br)))));
            frontLeft.setPower(fl / max);
            backLeft.setPower(bl / max);
            frontRight.setPower(fr / max);
            backRight.setPower(br / max);

            telemetry.addData("Tracking", track);
            telemetry.addData("Strafe Power", strafe);
            telemetry.addData("Forward Power", forward);
            telemetry.addData("Rotation Angle", angle);
            telemetry.addData("Servo Pos", rotateServo.getPosition());
            telemetry.update();
        }
    }
}