package org.firstinspires.ftc.teamcode.Camera;

import com.acmerobotics.roadrunner.geometry.Pose2d;

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
}
