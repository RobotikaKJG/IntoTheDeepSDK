package org.firstinspires.ftc.teamcode.Autonomous.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;

public class SampleTrajectories {

    SampleMecanumDrive drive;
    TrajectorySequence threeSampleTrajectory;

    private final Pose2d startPose = new Pose2d(-35, -60, Math.toRadians(0));
    public SampleTrajectories(SampleMecanumDrive drive) {
        this.drive = drive;
        fillVariables();
    }

    private void fillVariables() {
        threeSampleTrajectory = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(-50, -60, Math.toRadians(0)))
                .waitSeconds(2)
                .lineToLinearHeading(new Pose2d(-48, -48,Math.toRadians(90)))
                .waitSeconds(5)
                .lineToConstantHeading(new Vector2d(-61, -48))
                .build();
    }

    public TrajectorySequence threeSampleTrajectory() {
        return threeSampleTrajectory;
    }

    public Pose2d getStartPose() {
        return startPose;
    }
}
