package org.firstinspires.ftc.teamcode.Autonomous.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.Autonomous.SampleAuton;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;

public class SampleTrajectories {

    SampleMecanumDrive drive;
    TrajectorySequence preloadTrajectory;
    TrajectorySequence secondSampleIntakePath;
    TrajectorySequence secondSampleOuttakePath;
    TrajectorySequence thirdSamplePath;
    TrajectorySequence forthSampleIntakePath;
    TrajectorySequence forthSampleOuttakePath;
    TrajectorySequence fiveSampleIntakePath1;
    TrajectorySequence fiveSampleOuttakePath;
    TrajectorySequence moveToSub;


    private final Pose2d startPose = new Pose2d(-38, -64, Math.toRadians(0));
    public SampleTrajectories(SampleMecanumDrive drive) {
        this.drive = drive;
        fillVariables();
    }

    private void fillVariables() {
        preloadTrajectory = drive.trajectorySequenceBuilder(startPose)
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))

                .lineToLinearHeading(new Pose2d(-55, -53.5, Math.toRadians(69)))
//                .waitSeconds(0.5)
                .build();

        secondSampleIntakePath = drive.trajectorySequenceBuilder(preloadTrajectory.end())
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))

                .lineToLinearHeading(new Pose2d(-54, -47, Math.toRadians(64)))
                .build();

        secondSampleOuttakePath = drive.trajectorySequenceBuilder(secondSampleIntakePath.end())
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))

                .lineToLinearHeading(new Pose2d(-61, -53, Math.toRadians(69)))
                .build();

        thirdSamplePath = drive.trajectorySequenceBuilder(secondSampleOuttakePath.end())
            // third sample intake
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))

            .lineToLinearHeading(new Pose2d(-61.5, -51.5, Math.toRadians(83)))
//            .waitSeconds(0.5)
            .build();


        forthSampleIntakePath = drive.trajectorySequenceBuilder(thirdSamplePath.end())
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))

                .lineToLinearHeading(new Pose2d(-60.5, -52, Math.toRadians(115)))
//                .waitSeconds(0.5)
                .build();

        forthSampleOuttakePath = drive.trajectorySequenceBuilder(forthSampleIntakePath.end())
                .lineToLinearHeading(new Pose2d(-60, -54, Math.toRadians(75)))
//                .waitSeconds(0.5)
                .build();

        fiveSampleIntakePath1 = drive.trajectorySequenceBuilder(new Pose2d(-54.5, -50, Math.toRadians(65)))
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(100, Math.toRadians(180), 13.5))
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(80,50))
                .lineToSplineHeading(new Pose2d(-32, -8, Math.toRadians(0)))
                .build();

        fiveSampleOuttakePath = drive.trajectorySequenceBuilder(SampleAuton.getFifthIntakePose())
                .lineToSplineHeading(new Pose2d(-55, -58, Math.toRadians(35)))
                .build();

        moveToSub = drive.trajectorySequenceBuilder(new Pose2d(-38, -8, Math.toRadians(0)))
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(50, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(30, 10))

                .lineTo(new Vector2d(-28, -8))
                .build();


    }





    public TrajectorySequence preloadTrajectory() {
        return preloadTrajectory;
    }

    public TrajectorySequence secondSampleIntakePath() {
        return secondSampleOuttakePath;
    }

    public TrajectorySequence secondSampleOuttakePath() {
        return secondSampleOuttakePath;
    }

    public TrajectorySequence followThirdSampleIntakePath() {
        return thirdSamplePath;
    }

    public TrajectorySequence followForthSampleIntakePath() {
        return forthSampleIntakePath;
    }

    public TrajectorySequence followForthSampleOuttakePath() {
        return forthSampleOuttakePath;
    }

    public TrajectorySequence followFiveSampleOuttakePath() {
        return fiveSampleOuttakePath;
    }

    public TrajectorySequence goToSub() {
        return moveToSub;
    }

    public Pose2d getStartPose() {
        return startPose;
    }
}
