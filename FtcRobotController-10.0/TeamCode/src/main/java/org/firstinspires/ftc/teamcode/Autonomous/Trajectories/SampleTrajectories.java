package org.firstinspires.ftc.teamcode.Autonomous.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;

public class SampleTrajectories {

    SampleMecanumDrive drive;
    TrajectorySequence preloadTrajectory;
    TrajectorySequence follow2ndSamplePath;
    TrajectorySequence thirdSampleIntakePath;
    TrajectorySequence thirdSampleOuttakePath;
    TrajectorySequence forthSampleIntakePath;
    TrajectorySequence forthSampleOuttakePath;


    private final Pose2d startPose = new Pose2d(-38, -61, Math.toRadians(0));
    public SampleTrajectories(SampleMecanumDrive drive) {
        this.drive = drive;
        fillVariables();
    }

    private void fillVariables() {
        preloadTrajectory = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(-57, -50, Math.toRadians(65)))
                .waitSeconds(0.5)
                .build();

        follow2ndSamplePath = drive.trajectorySequenceBuilder(new Pose2d(-57, -50, Math.toRadians(65)))  // Starts from last position
                .lineToLinearHeading(new Pose2d(-57, -49, Math.toRadians(70)))
                .waitSeconds(0.5)
                .build();

        thirdSampleIntakePath = drive.trajectorySequenceBuilder(new Pose2d(-57, -49, Math.toRadians(70)))
            // third sample intake
            .lineToLinearHeading(new Pose2d(-57, -47, Math.toRadians(90)))
            .waitSeconds(0.5)
            .build();

        thirdSampleOuttakePath = drive.trajectorySequenceBuilder(new Pose2d(-57, -47, Math.toRadians(90)))
            // third sample outtake
            .lineToLinearHeading(new Pose2d(-57, -50, Math.toRadians(65)))
            .waitSeconds(0.5)
            .build();

        forthSampleIntakePath = drive.trajectorySequenceBuilder(new Pose2d(-57, -50, Math.toRadians(65)))
                .lineToLinearHeading(new Pose2d(-57, -47, Math.toRadians(115)))
                .waitSeconds(0.5)
                .build();

        forthSampleOuttakePath = drive.trajectorySequenceBuilder(new Pose2d(-57, -47, Math.toRadians(115)))
                .lineToLinearHeading(new Pose2d(-55, -52, Math.toRadians(35)))
                .waitSeconds(0.5)
                .build();
    }

    public TrajectorySequence preloadTrajectory() {
        return preloadTrajectory;
    }

    public TrajectorySequence follow2ndSamplePath() {
        return follow2ndSamplePath;
    }

    public TrajectorySequence followThirdSampleIntakePath() {
        return thirdSampleIntakePath;
    }

    public TrajectorySequence followThirdSampleOuttakePath() {
        return thirdSampleOuttakePath;
    }

    public TrajectorySequence followForthSampleIntakePath() {
        return forthSampleIntakePath;
    }

    public TrajectorySequence followForthSampleOuttakePath() {
        return forthSampleOuttakePath;
    }

    public Pose2d getStartPose() {
        return startPose;
    }
}
