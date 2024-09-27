package org.firstinspires.ftc.teamcode.Camera;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;

@Config
public class AprilTagConstants {
    public static final double cameraXOffset = 0.196;
    public static final double rightCameraYOffset = 0.07836;
    public static final double leftCameraYOffset = -0.07836;
    public static final Pose2d redAprilTagPose1 = new Pose2d(60, -28, Math.toRadians(0)); //should be correct?
    public static final Pose2d redAprilTagPose2 = new Pose2d(60, -35, Math.toRadians(0));
    public static final Pose2d redAprilTagPose3 = new Pose2d(60, -41.5, Math.toRadians(0));
    public static final Pose2d blueAprilTagPose1 = new Pose2d(60, 41.5, Math.toRadians(0));
    public static final Pose2d blueAprilTagPose2 = new Pose2d(60, 35, Math.toRadians(180));
    public static final Pose2d blueAprilTagPose3 = new Pose2d(60, 28, Math.toRadians(0));
    public static double fx = 578.272;     // Focal length in x direction (pixels)
    public static double fy = 578.272;     // Focal length in y direction (pixels)
    public static double cx = 402.145;     // Optical center x (pixels)
    public static double cy = 221.506;     // Optical center y (pixels)

}
