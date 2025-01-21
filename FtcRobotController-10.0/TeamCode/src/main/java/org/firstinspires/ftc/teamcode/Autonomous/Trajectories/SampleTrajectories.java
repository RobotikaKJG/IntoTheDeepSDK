package org.firstinspires.ftc.teamcode.Autonomous.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;

public class SampleTrajectories {

    SampleMecanumDrive drive;
    TrajectorySequence preloadTrajectory;

    private final Pose2d startPose = new Pose2d(-38, -61, Math.toRadians(0));
    public SampleTrajectories(SampleMecanumDrive drive) {
        this.drive = drive;
        fillVariables();
    }

    private void fillVariables() {
        preloadTrajectory = drive.trajectorySequenceBuilder(startPose)
                //.waitSeconds(2)
                .lineToLinearHeading(new Pose2d(-52.75, -52.75, Math.toRadians(45)))
                .waitSeconds(1.5)
                .lineToLinearHeading(new Pose2d(-50, -48,Math.toRadians(90)))
                .waitSeconds(3)
                .lineToLinearHeading(new Pose2d(-54.5, -51, Math.toRadians(35)))
                .waitSeconds(3)
                .lineToLinearHeading(new Pose2d(-60,-48,Math.toRadians(95)))
                .waitSeconds(3)
                .lineToLinearHeading(new Pose2d(-52, -52.5, Math.toRadians(45)))
                .waitSeconds(3)
                .lineToLinearHeading(new Pose2d(-55,-47, Math.toRadians(135)))
                .waitSeconds(3)
                .lineToLinearHeading(new Pose2d(-52, -52.5, Math.toRadians(30)))
                .build();
    }

    public TrajectorySequence preloadTrajectory() {
        return preloadTrajectory;
    }

    public Pose2d getStartPose() {
        return startPose;
    }
}