package org.firstinspires.ftc.teamcode.Camera.SampleDetection;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class SampleDetection {

    private OpenCvCamera camera;
    private SampleDetectionPipeline pipeline;
    private Telemetry telemetry;

    // Constructor that takes telemetry and hardware map as parameters
    public SampleDetection(Telemetry telemetry, HardwareMap hardwareMap) {
        this.telemetry = telemetry;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        // Set up the pipeline
        pipeline = new SampleDetectionPipeline();
        camera.setPipeline(pipeline);
    }

    // Initialize and open the camera
    public void initializeCamera() {
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
    }

    // Process frame and update telemetry with detected object information
    public void updateTelemetry() {
        if (pipeline.getOrientationDescription() != null) {
            telemetry.addData("Object Info", pipeline.getOrientationDescription());
        } else {
            telemetry.addData("Object Info", "No yellow object detected");
        }
        telemetry.update();
    }

    // Stop camera streaming
    public void stopCamera() {
        camera.stopStreaming();
    }
}
