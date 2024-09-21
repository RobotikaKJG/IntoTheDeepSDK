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
        // Instantiate the AprilTagDetector class
        aprilTagDetector = new AprilTagDetector();

        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Retrieve the latest AprilTag detections
            ArrayList<AprilTagDetection> detections = aprilTagDetector.getDetections();

            if (!detections.isEmpty()) {
                for (AprilTagDetection detection : detections) {
                    // Retrieve the known field position of the detected AprilTag
                    double[] fieldTagPosition = getFieldTagPosition(detection.id);

                    if (fieldTagPosition != null) {
                        // Calculate the robot's position relative to the field based on the tag's position
                        double robotX = fieldTagPosition[0] - detection.pose.x; // X coordinate on the field
                        double robotZ = fieldTagPosition[1] - detection.pose.z; // Z coordinate on the field
                        double robotY = detection.pose.y;  // Y is the vertical position (height)

                        // Print the robot's 3D coordinates (X, Y, Z) to telemetry
                        telemetry.addLine(String.format(Locale.US, "Detected tag ID=%d", detection.id));
                        telemetry.addLine(String.format(Locale.US, "Robot X: %.2f meters", robotX));
                        telemetry.addLine(String.format(Locale.US, "Robot Y: %.2f meters (Height)", robotY));
                        telemetry.addLine(String.format(Locale.US, "Robot Z: %.2f meters", robotZ));
                    } else {
                        telemetry.addLine("Tag ID not recognized for field positioning");
                    }
                }
            } else {
                telemetry.addLine("No tags detected");
            }

            telemetry.update();
            sleep(20); // Small delay to avoid spamming telemetry
        }
    }

    /**
     * This method returns the (x, z) position of a tag on the field given its ID.
     * Replace the placeholder coordinates with the actual positions of your tags.
     */
    private double[] getFieldTagPosition(int tagID) {
        switch (tagID) {
            case 1:
                return new double[]{2.0, 3.0}; // Example coordinates for tag ID 1 (X, Z)
            case 2:
                return new double[]{4.0, 1.0}; // Example coordinates for tag ID 2 (X, Z)
            // Add cases for other tag IDs as needed
            default:
                return null; // Unknown tag ID
        }
    }
}
