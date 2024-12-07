package org.firstinspires.ftc.teamcode.Camera.AprilTag;  // Adjust the package name based on your folder structure

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.apriltag.AprilTagDetectionPipeline;

import java.util.ArrayList;

@TeleOp(name = "AprilTag X, Z Coordinate Detection", group = "Linear Opmode")
public class AprilTagDetectionOpMode extends LinearOpMode {  // Use your custom class name
    // Constants
    private static final double TAG_SIZE = 0.1016;  // AprilTag size in meters (10.16 cm)
    private static final double METERS_TO_INCHES = 39.3701;
    private static final double SCALING_FACTOR = 1.276;  // Scaling factor for distance

    // OpenCV variables
    OpenCvCamera webcam;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    @Override
    public void runOpMode() {
        // Initialize the camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        // Initialize the AprilTag detection pipeline
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(TAG_SIZE, 550, 550, 320, 240);

        // Set the pipeline for the camera
        webcam.setPipeline(aprilTagDetectionPipeline);

        // Open the camera asynchronously with error handling
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera error", errorCode);
                telemetry.update();
            }
        });

        // Wait for the start button to be pressed
        waitForStart();

        // Main loop to detect the AprilTag and find the distance
        while (opModeIsActive()) {
            // Get the list of detected AprilTags
            ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getLatestDetections();

            if (detections.size() > 0) {
                // Loop through the detections and find the tag with ID 11
                for (AprilTagDetection detection : detections) {
                    if (detection.id == 11) {
                        // Get the X and Z coordinates from the tag's pose
                        double distanceMeters = detection.pose.z;  // Z component gives the distance in meters
                        double horizontalOffsetMeters = detection.pose.x;  // X component gives the horizontal offset in meters

                        // Convert to inches
                        double distanceInches = distanceMeters * METERS_TO_INCHES;
                        double horizontalOffsetInches = horizontalOffsetMeters * METERS_TO_INCHES;

                        // Apply scaling factor to the distance
                        double adjustedDistanceInches = distanceInches * SCALING_FACTOR;

                        // Display the X and Z coordinates in telemetry
                        telemetry.addData("AprilTag ID", detection.id);
                        telemetry.addData("X (Distance)", "%.2f inches", adjustedDistanceInches);  // Distance in inches
                        telemetry.addData("Z (Offset)", "%.2f inches", horizontalOffsetInches);  // Horizontal offset in inches
                        telemetry.update();
                    }
                }
            } else {
                telemetry.addData("No AprilTag detected", "");
                telemetry.update();
            }

            sleep(20);  // Small sleep to prevent flooding the telemetry
        }
    }
}
