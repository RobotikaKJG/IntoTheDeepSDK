package org.firstinspires.ftc.teamcode.Autonomous.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;

public class SpecimenTrajectories {

    SampleMecanumDrive drive;
    TrajectorySequence hangFirstSpecimen;
    TrajectorySequence collectSamples;
    TrajectorySequence hangSecondSpecimen;
    TrajectorySequence hangThirdSpecimen;
    TrajectorySequence hangFourthSpecimen;
    TrajectorySequence hangFifthSpecimen;
    TrajectorySequence park;

    private final Pose2d startPose = new Pose2d(10, -61,Math.toRadians(-90));

    public SpecimenTrajectories(SampleMecanumDrive drive) {
        this.drive = drive;
    }

    private void fillVariables() {
        hangFirstSpecimen = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(10, -32,Math.toRadians(-90)))
                .build();

        collectSamples = drive.trajectorySequenceBuilder(hangFirstSpecimen.end())
                .splineToConstantHeading(new Vector2d(35, -30), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(35, -25), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(40, -5), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(48, -10), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(48, -35), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(48, -10), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(58, -10), Math.toRadians(-90))
                .splineTo(new Vector2d(58, -35), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(58, -10), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(61, -10), Math.toRadians(-90))
                .splineTo(new Vector2d(61, -50), Math.toRadians(-90))
                .build();

        hangSecondSpecimen = drive.trajectorySequenceBuilder(collectSamples.end())
                .lineToLinearHeading(new Pose2d(45,-55,Math.toRadians(90)))
                .waitSeconds(0.5)
                .lineToLinearHeading(new Pose2d(5,-33,Math.toRadians(-85)))
                .lineToLinearHeading(new Pose2d(5,-32,Math.toRadians(-90)))
                .build();

        hangThirdSpecimen = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                .lineToLinearHeading(new Pose2d(45,-55,Math.toRadians(90)))
                .waitSeconds(0.5)
                .lineToLinearHeading(new Pose2d(2,-33,Math.toRadians(-85)))
                .lineToLinearHeading(new Pose2d(2,-32,Math.toRadians(-90)))
                .build();

        hangFourthSpecimen = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                .lineToLinearHeading(new Pose2d(45,-55,Math.toRadians(90)))
                .waitSeconds(0.5)
                .lineToLinearHeading(new Pose2d(-1,-33,Math.toRadians(-85)))
                .lineToLinearHeading(new Pose2d(-1,-32,Math.toRadians(-90)))
                .build();

        hangFifthSpecimen = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                .lineToLinearHeading(new Pose2d(45,-55,Math.toRadians(90)))
                .waitSeconds(0.5)
                .lineToLinearHeading(new Pose2d(-4,-33,Math.toRadians(-85)))
                .lineToLinearHeading(new Pose2d(-4,-32,Math.toRadians(-90)))
                .build();

        park = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                .lineToLinearHeading(new Pose2d(25,-45 ,Math.toRadians(-45)))
                .build();

    }

    public TrajectorySequence hangFirstSpecimen() {
        return hangFirstSpecimen;
    }

    public TrajectorySequence collectSamples() {
        return collectSamples;
    }

    public TrajectorySequence hangSecondSpecimen() {
        return hangSecondSpecimen;
    }

    public TrajectorySequence hangThirdSpecimen() {
        return hangThirdSpecimen;
    }

    public TrajectorySequence hangFourthSpecimen() {
        return hangFourthSpecimen;
    }

    public TrajectorySequence hangFifthSpecimen() {
        return hangFifthSpecimen;
    }

    public TrajectorySequence park() {
        return park;
    }

    public Pose2d getStartPose() {
        return startPose;
    }
}
