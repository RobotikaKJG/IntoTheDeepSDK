package org.firstinspires.ftc.teamcode.Localisation.Camera;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

import org.firstinspires.ftc.teamcode.Camera.AprilTag.AprilTagDetectionPipeline;

public class AprilTagProcessor {

    // Constants
    private static final double TAG_SIZE = 0.1016;  // AprilTag size in meters (10.16 cm)
    private static final double METERS_TO_INCHES = 39.3701;
    private static final double SCALING_FACTOR = 1.276;  // Scaling factor for distance

    private OpenCvCamera webcam;
    private AprilTagDetectionPipeline aprilTagDetectionPipeline;

    /**
     * Initializes the camera and starts streaming.
     * @param hardwareMap The hardware map used to get the camera from the configuration.
     */
    public void initializeCamera(HardwareMap hardwareMap) {
        // Initialize the camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        // Initialize the AprilTag detection pipeline
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(TAG_SIZE, 550, 550, 320, 240);

        // Set the pipeline for the camera
        webcam.setPipeline(aprilTagDetectionPipeline);

        // Open the camera asynchronously
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                System.out.println("Camera error: " + errorCode);  // Replace with telemetry if needed
            }
        });
    }

    /**
     * Processes the AprilTag detections and returns distance and offset.
     * @return A list of tag data (ID, X (distance), Z (offset)).
     */
    public ArrayList<String> processTags() {
        ArrayList<String> tagData = new ArrayList<>();

        ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getLatestDetections();

        if (detections.size() > 0) {
            for (AprilTagDetection detection : detections) {
                if (detection.id == 11) {
                    // Get the X and Z coordinates from the tag's pose
                    double distanceMeters = detection.pose.z;
                    double horizontalOffsetMeters = detection.pose.x;

                    // Convert to inches
                    double distanceInches = distanceMeters * METERS_TO_INCHES;
                    double horizontalOffsetInches = horizontalOffsetMeters * METERS_TO_INCHES;

                    // Apply scaling factor
                    double adjustedDistanceInches = distanceInches * SCALING_FACTOR;

                    // Format data for return
                    tagData.add(String.format("AprilTag ID: %d", detection.id));
                    tagData.add(String.format("X (Distance): %.2f inches", adjustedDistanceInches));
                    tagData.add(String.format("Z (Offset): %.2f inches", horizontalOffsetInches));
                }
            }
        } else {
            tagData.add("No AprilTag detected.");
        }

        return tagData;
    }
}