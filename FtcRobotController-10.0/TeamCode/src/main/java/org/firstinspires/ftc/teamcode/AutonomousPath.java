package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.trajectory.TrajectorySequence;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.CustomMecanumDrive;

@Autonomous(name = "Autonomous Path - Visualized")
public class AutonomousPath extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the drive
        CustomMecanumDrive drive = new CustomMecanumDrive(hardwareMap);

        // Define the starting pose
        Pose2d startPose = new Pose2d(-35, -61, Math.toRadians(90));
        drive.setPoseEstimate(startPose);

        // Build the trajectory sequence
        TrajectorySequence trajectorySequence = drive.trajectorySequenceBuilder(startPose)
                .splineToLinearHeading(new Pose2d(-48, -35, Math.toRadians(270)), Math.toRadians(90))
                .back(15)
                .splineToLinearHeading(new Pose2d(-50, -50, Math.toRadians(-135)), Math.toRadians(240))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(35, -25, Math.toRadians(90)), Math.toRadians(90))
                .setReversed(false)
                .splineToConstantHeading(new Vector2d(48, -10), Math.toRadians(0))
                .lineToConstantHeading(new Vector2d(48, -50))
                .build();

        telemetry.addData("Status", "Initialized and Waiting for Start");
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        // Follow the trajectory
        drive.followTrajectorySequence(trajectorySequence);

        telemetry.addData("Status", "Trajectory Complete");
        telemetry.update();
    }
}