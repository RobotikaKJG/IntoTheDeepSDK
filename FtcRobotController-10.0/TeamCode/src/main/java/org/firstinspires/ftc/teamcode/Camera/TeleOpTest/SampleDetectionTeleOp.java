package org.firstinspires.ftc.teamcode.Camera.TeleOpTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Camera.SampleDetection.SamplePipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;


@TeleOp(name = "Sample Detection TeleOp")
public class SampleDetectionTeleOp extends LinearOpMode {

    private OpenCvCamera camera;
    private SamplePipeline pipeline;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        // Set the pipeline
        pipeline = new SamplePipeline();
        camera.setPipeline(pipeline);

        // Open the camera asynchronously
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Error", errorCode);
                telemetry.update();
            }
        });

        telemetry.addData("Status", "Waiting for start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            // Fetch and display the corners of the detected object in telemetry
            if (pipeline.getCorners() != null) {
                for (int i = 0; i < pipeline.getCorners().length; i++) {
                    telemetry.addData("Corner " + i, pipeline.getCorners()[i]);
                }
            } else {
                telemetry.addData("No corners detected", "");
            }

            telemetry.update();
            sleep(50);  // Control the loop speed
        }

        // Stop the camera when done
        camera.stopStreaming();
    }
}
