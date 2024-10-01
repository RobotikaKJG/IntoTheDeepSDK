package org.firstinspires.ftc.teamcode.Camera;

import static org.firstinspires.ftc.teamcode.Camera.AprilTagConstants.cx;
import static org.firstinspires.ftc.teamcode.Camera.AprilTagConstants.cy;
import static org.firstinspires.ftc.teamcode.Camera.AprilTagConstants.fx;
import static org.firstinspires.ftc.teamcode.Camera.AprilTagConstants.fy;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.apriltag.AprilTagPose;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Config
@TeleOp(name = "AprilTag Detection Test", group = "Concept")
public class AprilTagDetector extends LinearOpMode {

    OpenCvCamera webcam;
    private AprilTagDetectionPipeline aprilTagDetectionPipeline;
    Telemetry telemetry2;
    HardwareMap hardwareMap2;

    @Override
    public void runOpMode() {
        initializeAprilTagDetector(telemetry, hardwareMap);
    }

    public void initializeAprilTagDetector(Telemetry telemetry, HardwareMap hardwareMap) {
        telemetry2 = telemetry;
        hardwareMap2 = hardwareMap;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap2.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap2.get(WebcamName.class, "Webcam 2"), cameraMonitorViewId);

        // Camera calibration values
        double tagsize = 0.1016;  // Tag size in meters


        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        webcam.setPipeline(aprilTagDetectionPipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                if (opModeIsActive()) {
                    telemetry2.addLine("Camera failed to open!");
                    telemetry2.update();
                }
            }
        });

        if (opModeIsActive()) {
            telemetry2.addLine("Waiting for start");
            telemetry2.update();
        }
    }

    public ArrayList<AprilTagDetection> getDetections() {
        // Ensure the pipeline is initialized before calling getLatestDetections()
        if (aprilTagDetectionPipeline == null) {
            if (opModeIsActive()) {
                telemetry2.addLine("AprilTag Detection Pipeline is not initialized");
                telemetry2.update();
            }
            return new ArrayList<>();  // Return an empty list to avoid null pointer exceptions
        }

        return aprilTagDetectionPipeline.getLatestDetections();
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
