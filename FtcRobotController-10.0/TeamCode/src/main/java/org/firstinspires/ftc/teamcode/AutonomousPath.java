package org.firstinspires.ftc.teamcode.DriveTrain;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Autonomous Path - Visualized")
public class AutonomousPath extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the drive
        CustomMecanumDrive drive = new CustomMecanumDrive(hardwareMap);

        // Define the starting pose
        Pose2d startPose = new Pose2d(-35, -61, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        // Build individual trajectories
        Trajectory trajectory1 = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(-48, -35), Math.toRadians(270))
                .build();

        // Get the end pose of trajectory1
        Pose2d trajectory1EndPose = trajectory1.end();

        Trajectory trajectory2 = drive.trajectoryBuilder(trajectory1EndPose)
                .lineToLinearHeading(new Pose2d(-50, -50, Math.toRadians(-135))) // Use lineToLinearHeading
                .build();

        // Get the end pose of trajectory2
        Pose2d trajectory2EndPose = trajectory2.end();

        Trajectory trajectory3 = drive.trajectoryBuilder(trajectory2EndPose, true) // Reversed trajectory
                .splineTo(new Vector2d(35, -25), Math.toRadians(90))
                .build();

        // Get the end pose of trajectory3
        Pose2d trajectory3EndPose = trajectory3.end();

        Trajectory trajectory4 = drive.trajectoryBuilder(trajectory3EndPose)
                .splineToConstantHeading(new Vector2d(48, -10), Math.toRadians(0))
                .lineToConstantHeading(new Vector2d(48, -50))
                .build();

        telemetry.addData("Status", "Initialized and Waiting for Start");
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        // Follow the trajectories sequentially
        drive.followTrajectory(trajectory1);
        drive.followTrajectory(trajectory2);
        drive.followTrajectory(trajectory3);
        drive.followTrajectory(trajectory4);

        telemetry.addData("Status", "Trajectory Complete");
        telemetry.update();
    }
}
