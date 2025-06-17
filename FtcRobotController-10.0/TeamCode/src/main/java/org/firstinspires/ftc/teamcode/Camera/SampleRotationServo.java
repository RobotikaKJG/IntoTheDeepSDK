package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ServoControl;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@TeleOp
public class SampleRotationServo extends LinearOpMode {

    OpenCvWebcam webcam;
    ColorAndRotationPipeline pipeline;

    ServoControl servoControl;
    final String servoName = "rotateServo";  // Make sure your config uses this name
    double servoPosition = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize servo control for a standard (non-continuous) servo
        servoControl = new ServoControl(hardwareMap, servoName, false);

        // Camera setup
        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"),
                cameraMonitorViewId
        );

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

        waitForStart();

        while (opModeIsActive()) {
            int detectedAngle = pipeline.getRotationAngle();

            if (detectedAngle < 0 || detectedAngle > 180) {
                telemetry.addData("Warning", "Invalid angle detected");
                telemetry.update();
                continue;
            }

            servoPosition = detectedAngle / 270.0; // Convert to servo range (0.0–0.6)

            // Invert if needed (optional)
            servoPosition = 1.0 - servoPosition; // Only if direction is reversed for your setup

            // Clamp to [0.0, 1.0] to be safe
            servoPosition = Math.max(0.0, Math.min(1.0, servoPosition));

            // Set servo
            servoControl.setServoPos(servoPosition);

            // Telemetry
            telemetry.addData("Detected Angle", detectedAngle);
            telemetry.addData("Servo Position", servoPosition);
            telemetry.update();
        }
    }
}
