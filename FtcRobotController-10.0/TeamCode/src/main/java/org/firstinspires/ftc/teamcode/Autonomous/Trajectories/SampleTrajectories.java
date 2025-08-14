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

                .lineToLinearHeading(new Pose2d(-55.5, -57.5, Math.toRadians(69)))
//                .waitSeconds(0.5)
                .build();

        secondSampleIntakePath = drive.trajectorySequenceBuilder(preloadTrajectory.end())
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))

                .lineToLinearHeading(new Pose2d(-54, -47, Math.toRadians(45)))
                .build();

        secondSampleOuttakePath = drive.trajectorySequenceBuilder(secondSampleIntakePath.end())
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))

                .lineToLinearHeading(new Pose2d(-57, -53, Math.toRadians(69)))
                .build();

        thirdSamplePath = drive.trajectorySequenceBuilder(secondSampleOuttakePath.end())
            // third sample intake
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))

            .lineToLinearHeading(new Pose2d(-59.5, -52.5, Math.toRadians(80)))
//            .waitSeconds(0.5)
            .build();


        forthSampleIntakePath = drive.trajectorySequenceBuilder(thirdSamplePath.end())
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))
                .waitSeconds(0.1)
                .lineToLinearHeading(new Pose2d(-57.5, -53.5, Math.toRadians(110)))//115)))
//                .waitSeconds(0.5)
                .build();

        forthSampleOuttakePath = drive.trajectorySequenceBuilder(forthSampleIntakePath.end())
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(40, Math.toRadians(50), 13.5))
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(10, 20))
                .waitSeconds(0.05)
                .lineToLinearHeading(new Pose2d(-58.5, -55.5, Math.toRadians(80)))

//                .waitSeconds(0.3)
//                .waitSeconds(0.5)
                .build();

        fiveSampleIntakePath1 = drive.trajectorySequenceBuilder(new Pose2d(-54.5, -50, Math.toRadians(65)))
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(100, Math.toRadians(180), 13.5))
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(80,50))
                .lineToSplineHeading(new Pose2d(-32, -8, Math.toRadians(0)))
                .build();

        fiveSampleOuttakePath = drive.trajectorySequenceBuilder(SampleAuton.getFifthIntakePose())
                .lineToSplineHeading(new Pose2d(-48, -63, Math.toRadians(35)))
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
