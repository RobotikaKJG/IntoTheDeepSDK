package org.firstinspires.ftc.teamcode.Camera;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.openftc.apriltag.AprilTagPose;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;
import java.util.Locale;

@TeleOp(name = "AprilTag Detection Example", group = "Concept")
public class AprilTagDetector extends LinearOpMode {

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvCamera webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 2"), cameraMonitorViewId);

        // Camera calibration values
        double tagsize = 0.166;  // Tag size in meters
        double fx = 578.272;     // Focal length in x direction (pixels)
        double fy = 578.272;     // Focal length in y direction (pixels)
        double cx = 402.145;     // Optical center x (pixels)
        double cy = 221.506;     // Optical center y (pixels)

        AprilTagDetectionPipeline aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        webcam.setPipeline(aprilTagDetectionPipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addLine("Camera failed to open!");
                telemetry.update();
            }
        });

        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            ArrayList<org.openftc.apriltag.AprilTagDetection> detections = aprilTagDetectionPipeline.getLatestDetections();

            if (!detections.isEmpty()) {
                for (org.openftc.apriltag.AprilTagDetection detection : detections) {
                    AprilTagDetectionPipeline.Pose pose = aprilTagPoseToOpenCvPose(detection.pose);

                    double[] eulerAngles = pose.getEulerAngles();
                    telemetry.addLine(String.format(Locale.US, "\nDetected tag ID=%d", detection.id));
                    telemetry.addLine(String.format(Locale.US, "Translation X: %.2f meters", detection.pose.x));
                    telemetry.addLine(String.format(Locale.US, "Translation Y: %.2f meters", detection.pose.y));
                    telemetry.addLine(String.format(Locale.US, "Translation Z: %.2f meters", detection.pose.z));
                    telemetry.addLine(String.format(Locale.US, "Rotation Yaw: %.2f degrees", eulerAngles[0]));
                    telemetry.addLine(String.format(Locale.US, "Rotation Pitch: %.2f degrees", eulerAngles[1]));
                    telemetry.addLine(String.format(Locale.US, "Rotation Roll: %.2f degrees", eulerAngles[2]));
                }
            } else {
                telemetry.addLine("No tags detected");
            }

            telemetry.update();
            sleep(20); // Small delay to avoid spamming telemetry
        }

        webcam.stopStreaming();
    }

    private AprilTagDetectionPipeline.Pose aprilTagPoseToOpenCvPose(AprilTagPose aprilTagPose) {
        AprilTagDetectionPipeline.Pose pose = new AprilTagDetectionPipeline.Pose();
        pose.tvec.put(0, 0, aprilTagPose.x);
        pose.tvec.put(1, 0, aprilTagPose.y);
        pose.tvec.put(2, 0, aprilTagPose.z);

        Mat R = new Mat(3, 3, CvType.CV_32F);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                R.put(i, j, aprilTagPose.R.get(i, j));
            }
        }

        Calib3d.Rodrigues(R, pose.rvec);

        return pose;
    }
}
