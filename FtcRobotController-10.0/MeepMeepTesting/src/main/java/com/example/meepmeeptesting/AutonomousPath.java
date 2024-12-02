package com.example.meepmeeptesting;

//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.trajectory.TrajectorySequence;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import org.firstinspires.ftc.teamcode.drive.CustomMecanumDrive;
//
//@Autonomous(name = "Autonomous Path - Visualized with Meep Meep")
//public class AutonomousPath extends LinearOpMode {
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        // Initialize your custom drive class
//        CustomMecanumDrive drive = new CustomMecanumDrive(hardwareMap);
//
//        // Define the start position (from Meep Meep)
//        Pose2d startPose = new Pose2d(-35, -61, Math.toRadians(90)); // Initial heading adjusted to 90 degrees
//        drive.setPoseEstimate(startPose);
//
//        // Create the trajectory sequence
//        TrajectorySequence trajectorySequence = drive.trajectorySequenceBuilder(startPose)
//                .waitSeconds(2)
//                .splineToLinearHeading(new Pose2d(-48, -35, Math.toRadians(270)), Math.toRadians(90))
//                .back(15)
//                .splineToLinearHeading(new Pose2d(-50, -50, Math.toRadians(-135)), Math.toRadians(240))
//                .waitSeconds(2)
//                .setReversed(true)
//                .splineToLinearHeading(new Pose2d(35, -25, Math.toRadians(90)), Math.toRadians(90))
//                .setReversed(false)
//                .splineToConstantHeading(new Vector2d(48, -10), Math.toRadians(0))
//                .lineToConstantHeading(new Vector2d(48, -50))
//                .waitSeconds(2)
//                .build();
//
//        telemetry.addData("Status", "Initialized and Waiting for Start");
//        telemetry.update();
//
//        waitForStart();
//
//        if (isStopRequested()) return;
//
//        // Follow the trajectory
//        drive.followTrajectorySequence(trajectorySequence);
//
//        telemetry.addData("Status", "Trajectory Complete");
//        telemetry.update();
//    }
//}