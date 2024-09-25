package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.openftc.apriltag.AprilTagDetection;
import java.util.ArrayList;
import java.util.Locale;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

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

                    double[] fieldTagPosition = getFieldTagPosition(detection.id);

                    if (fieldTagPosition != null) {
                        // Convert meters to inches (1 meter = 39.3701 inches)
                        double robotX = (fieldTagPosition[0] - detection.pose.x) * 39.3701; // X coordinate in inches
                        double robotZ = (fieldTagPosition[1] - detection.pose.z) * 39.3701; // Z coordinate in inches
                        double robotY = detection.pose.y * 39.3701;  // Y is the vertical position in inches


                        MatrixF rotationMatrix = detection.pose.R;


                        double sy = Math.sqrt(rotationMatrix.get(0, 0) * rotationMatrix.get(0, 0) +
                                rotationMatrix.get(1, 0) * rotationMatrix.get(1, 0));

                        boolean singular = sy < 1e-6;

                        double yaw, pitch, roll;
                        if (!singular) {
                            yaw = Math.atan2(rotationMatrix.get(2, 1), rotationMatrix.get(2, 2));
                            pitch = Math.atan2(-rotationMatrix.get(2, 0), sy);
                            roll = Math.atan2(rotationMatrix.get(1, 0), rotationMatrix.get(0, 0));
                        } else {
                            yaw = Math.atan2(-rotationMatrix.get(1, 2), rotationMatrix.get(1, 1));
                            pitch = Math.atan2(-rotationMatrix.get(2, 0), sy);
                            roll = 0;
                        }


                        yaw = Math.toDegrees(yaw);
                        pitch = Math.toDegrees(pitch);
                        roll = Math.toDegrees(roll);

                        // Print the robot's 3D coordinates (X, Y, Z) and angles to telemetry
                        telemetry.addLine(String.format(Locale.US, "Detected tag ID=%d", detection.id));
                        telemetry.addLine(String.format(Locale.US, "Robot X: %.2f inches", robotX));
                        telemetry.addLine(String.format(Locale.US, "Robot Y: %.2f inches (Height)", robotY));
                        telemetry.addLine(String.format(Locale.US, "Robot Z: %.2f inches", robotZ));
                        telemetry.addLine(String.format(Locale.US, "Yaw: %.2f degrees", yaw));
                        telemetry.addLine(String.format(Locale.US, "Pitch: %.2f degrees", pitch));
                        telemetry.addLine(String.format(Locale.US, "Roll: %.2f degrees", roll));
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
