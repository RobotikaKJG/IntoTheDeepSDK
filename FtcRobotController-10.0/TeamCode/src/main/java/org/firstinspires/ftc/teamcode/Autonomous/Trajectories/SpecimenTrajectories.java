package org.firstinspires.ftc.teamcode.Autonomous.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;

public class SpecimenTrajectories {

    SampleMecanumDrive drive;
    TrajectorySequence hangFirstSpecimen;
    TrajectorySequence collectSamples;
    TrajectorySequence goToTakeSecondSpecimen;
    TrajectorySequence hangSecondSpecimen;
    TrajectorySequence goToTakeThirdSpecimen;
    TrajectorySequence hangThirdSpecimen;
    TrajectorySequence park;

    private final Pose2d startPose = new Pose2d(7, -61,Math.toRadians(-90));

    public SpecimenTrajectories(SampleMecanumDrive drive) {
        this.drive = drive;
        fillVariables();
    }

    private void fillVariables() {
        hangFirstSpecimen = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(10, -29,Math.toRadians(-90)))
                .build();

        collectSamples = drive.trajectorySequenceBuilder(hangFirstSpecimen.end())
                .splineToConstantHeading(new Vector2d(35, -30), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(35, -30), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(35, -25), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(40, -5), Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(49, -10), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(49, -35), Math.toRadians(-90))
                .build();

        goToTakeSecondSpecimen = drive.trajectorySequenceBuilder(collectSamples.end())
                .lineToLinearHeading(new Pose2d(48,-57,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(48,-60,Math.toRadians(90)))
                .build();

        hangSecondSpecimen = drive.trajectorySequenceBuilder(goToTakeSecondSpecimen.end())
                .lineToLinearHeading(new Pose2d(5,-33,Math.toRadians(-85)))
                .lineToLinearHeading(new Pose2d(5,-30,Math.toRadians(-90)))
                .build();

        goToTakeThirdSpecimen = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                .lineToLinearHeading(new Pose2d(48,-57,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(48,-61,Math.toRadians(90)))
                .build();

        hangThirdSpecimen = drive.trajectorySequenceBuilder(goToTakeThirdSpecimen.end())
                .lineToLinearHeading(new Pose2d(2,-33,Math.toRadians(-85)))
                .lineToLinearHeading(new Pose2d(2,-28.5,Math.toRadians(-90)))
                .build();

        park = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                //.lineToLinearHeading(new Pose2d(45,-50 ,Math.toRadians(-45)))
                .splineTo(new Vector2d(45,-50),Math.toRadians(-45))
                .build();

    }

    public TrajectorySequence hangFirstSpecimen() {
        return hangFirstSpecimen;
    }

    public TrajectorySequence collectSamples() {
        return collectSamples;
    }

    public TrajectorySequence goToTakeSecondSpecimen(){
        return goToTakeSecondSpecimen;
    }

    public TrajectorySequence hangSecondSpecimen() {
        return hangSecondSpecimen;
    }

    public TrajectorySequence goToTakeThirdSpecimen() {
        return goToTakeThirdSpecimen;
    }

    public TrajectorySequence hangThirdSpecimen() {
        return hangThirdSpecimen;
    }

    public TrajectorySequence park() {
        return park;
    }

    public Pose2d getStartPose() {
        return startPose;
    }
}
