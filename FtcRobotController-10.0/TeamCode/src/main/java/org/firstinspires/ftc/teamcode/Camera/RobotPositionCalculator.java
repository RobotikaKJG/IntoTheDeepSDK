package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.openftc.apriltag.AprilTagDetection;
import java.util.ArrayList;
import java.util.Locale;

@TeleOp(name = "Robot Position Calculator 3D", group = "Concept")
public class RobotPositionCalculator extends LinearOpMode {

    private AprilTagDetector aprilTagDetector;

    @Override
    public void runOpMode() {
        // Initialize the AprilTagDetector
        aprilTagDetector = new AprilTagDetector();
        aprilTagDetector.initializeAprilTagDetector(telemetry, hardwareMap);

        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Retrieve the latest AprilTag detections
            ArrayList<AprilTagDetection> detections = aprilTagDetector.getDetections();

            if (detections != null && !detections.isEmpty()) {
                for (AprilTagDetection detection : detections) {
                    // Retrieve the known field position of the detected AprilTag
                    double[] fieldTagPosition = getFieldTagPosition(detection.id);

                    if (fieldTagPosition != null) {
                        // Convert meters to inches (1 meter = 39.3701 inches)
                        double robotX = (fieldTagPosition[0] - detection.pose.x) * 39.3701; // X coordinate in inches
                        double robotZ = (fieldTagPosition[1] - detection.pose.z) * 39.3701; // Z coordinate in inches
                        double robotY = detection.pose.y * 39.3701;  // Y is the vertical position in inches

                        // Calculate the angle from the AprilTag to the robot's camera
                        double angleToTag = Math.toDegrees(Math.atan2(detection.pose.y, detection.pose.z)); // Angle in degrees

                        // Print the robot's 3D coordinates (X, Y, Z) and angle to telemetry
                        telemetry.addLine(String.format(Locale.US, "Detected tag ID=%d", detection.id));
                        telemetry.addLine(String.format(Locale.US, "Robot X: %.2f inches", robotX));
                        telemetry.addLine(String.format(Locale.US, "Robot Y: %.2f inches (Height)", robotY));
                        telemetry.addLine(String.format(Locale.US, "Robot Z: %.2f inches", robotZ));
                        telemetry.addLine(String.format(Locale.US, "Angle to Tag: %.2f degrees", angleToTag));
                    } else {
                        telemetry.addLine("Tag ID not recognized for field positioning");
                    }
                }
            } else {
                telemetry.addLine("No tags detected");
            }

            telemetry.update();
            sleep(20);  // Small delay to avoid spamming telemetry
        }

        // Ensure the camera stops streaming after the OpMode ends
        if (aprilTagDetector.webcam != null) {
            aprilTagDetector.webcam.stopStreaming();
        }
    }

    /**
     * This method returns the (x, z) position of a tag on the field given its ID.
     * Replace the placeholder coordinates with the actual positions of your tags.
     */
    private double[] getFieldTagPosition(int tagID) {
        switch (tagID) {
            case 11:
                return new double[]{0.0, 0.0}; // Example coordinates for tag ID 1 (X, Z)
            case 4:
                return new double[]{4.0, 1.0}; // Example coordinates for tag ID 4 (X, Z)
            // Add cases for other tag IDs as needed
            default:
                return null; // Unknown tag ID
        }
    }
}
