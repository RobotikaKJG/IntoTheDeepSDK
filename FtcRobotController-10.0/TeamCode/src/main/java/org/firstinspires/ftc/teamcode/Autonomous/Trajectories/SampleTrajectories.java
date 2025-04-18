package org.firstinspires.ftc.teamcode.Autonomous.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.Autonomous.SampleAuton;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.Autonomous.SampleAuton;

public class SampleTrajectories {

    SampleMecanumDrive drive;
    TrajectorySequence preloadTrajectory;
    TrajectorySequence follow2ndSamplePath;
    TrajectorySequence thirdSampleIntakePath;
    TrajectorySequence thirdSampleOuttakePath;
    TrajectorySequence forthSampleIntakePath;
    TrajectorySequence forthSampleOuttakePath;
    TrajectorySequence fiveSampleIntakePath1;
    TrajectorySequence fiveSampleOuttakePath;
    TrajectorySequence moveToSub;


    private final Pose2d startPose = new Pose2d(-38, -64, Math.toRadians(0)); // new Pose2d(-55, -52, Math.toRadians(35))
    private final Pose2d basketPose = new Pose2d(-58.5, -51, Math.toRadians(69));
    public SampleTrajectories(SampleMecanumDrive drive) {
        this.drive = drive;
        fillVariables();
    }

    private void fillVariables() {
        preloadTrajectory = drive.trajectorySequenceBuilder(startPose)
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))

                .lineToLinearHeading(basketPose)
//                .waitSeconds(0.5)
                .build();

        follow2ndSamplePath = drive.trajectorySequenceBuilder(basketPose)
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))

                .lineToLinearHeading(new Pose2d(-57.5, -52, Math.toRadians(69)))
                .build();


        thirdSampleIntakePath = drive.trajectorySequenceBuilder(new Pose2d(-57.5, -54, Math.toRadians(69)))
            // third sample intake
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))

            .lineToLinearHeading(new Pose2d(-60, -52, Math.toRadians(88)))
//            .waitSeconds(0.5)
            .build();


        forthSampleIntakePath = drive.trajectorySequenceBuilder(new Pose2d(-55.5, -50, Math.toRadians(88)))
                .setVelConstraint(SampleMecanumDrive.getVelocityConstraint(80, Math.toRadians(180), 13.5)) // Increase max speed
                .setAccelConstraint(SampleMecanumDrive.getAccelerationConstraint(20, 30))

                .lineToLinearHeading(new Pose2d(-60.5, -52, Math.toRadians(115)))
//                .waitSeconds(0.5)
                .build();

        forthSampleOuttakePath = drive.trajectorySequenceBuilder(new Pose2d(-60.5, -52, Math.toRadians(115)))
                .lineToLinearHeading(new Pose2d(-60, -54, Math.toRadians(70)))
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

    public TrajectorySequence followFiveSampleIntakePath() {
        return fiveSampleIntakePath1;
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
