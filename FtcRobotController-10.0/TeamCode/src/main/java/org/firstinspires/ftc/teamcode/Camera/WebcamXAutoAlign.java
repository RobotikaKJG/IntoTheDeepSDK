package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.EdgeDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@TeleOp
public class WebcamXAutoAlign extends LinearOpMode {
    OpenCvWebcam webcam;
    ColorDetectionPipeline pipeline;

    DcMotor frontLeft, frontRight, backLeft, backRight;
    GoBildaPinpointDriver pinpointDriver;

    final double centerY = 160; // for 320px width
    final double kp = 0.003; // Tune this!
    final double tolerance = 5; // Pixels
    private boolean track = false;

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

        pipeline = new ColorDetectionPipeline(320);
        pipeline.setTargetColor(ColorDetectionPipeline.TargetColor.RED);
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

            pinpointDriver.update();

            if (gamepad1.triangle) {
                return;
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
            double rx = gamepad1.right_stick_x; // rotation

            // Combine movement: manual Y + auto strafe X + manual rotate
            double fl = y + strafe + rx;
            double bl = y - strafe + rx;
            double fr = y - strafe - rx;
            double br = y + strafe - rx;

            // Normalize powers
            double max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(bl), Math.max(Math.abs(fr), Math.abs(br)))));
            frontLeft.setPower(fl / max);
            backLeft.setPower(bl / max);
            frontRight.setPower(fr / max);
            backRight.setPower(br / max);

            telemetry.addData("ObjectY", objectY);
            telemetry.addData("Strafe Power", strafe);
            telemetry.addData("Heading (°)", Math.toDegrees(pinpointDriver.getHeading()));
            telemetry.update();
        }
    }
}