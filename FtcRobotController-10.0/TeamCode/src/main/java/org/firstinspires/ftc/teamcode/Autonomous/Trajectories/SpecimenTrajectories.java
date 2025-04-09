package org.firstinspires.ftc.teamcode.Autonomous.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.Roadrunner.DriveConstants;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;

public class SpecimenTrajectories {

    SampleMecanumDrive drive;
    TrajectorySequence hangFirstSpecimen;
    TrajectorySequence collectFirstSample;
    TrajectorySequence dropFirstSample;
    TrajectorySequence collectSecondSample;
    TrajectorySequence dropSecondSample;
    TrajectorySequence collectThirdSample;
    TrajectorySequence dropThirdSample;
    TrajectorySequence goToTakeSecondSpecimen;
    TrajectorySequence hangSecondSpecimen;
    TrajectorySequence goToTakeThirdSpecimen;
    TrajectorySequence hangThirdSpecimen;
    TrajectorySequence goToTakeFourthSpecimen;
    TrajectorySequence hangFourthSpecimen;
    TrajectorySequence hangFifthSpecimen;
    TrajectorySequence park;

    private final Pose2d startPose = new Pose2d(7, -60,Math.toRadians(-90));

    public SpecimenTrajectories(SampleMecanumDrive drive) {
        this.drive = drive;
        fillVariables();
    }

    private void fillVariables() {
        hangFirstSpecimen = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(10, -28.5,Math.toRadians(-90)))
                .build();

        collectFirstSample = drive.trajectorySequenceBuilder(hangFirstSpecimen.end())
                .lineToLinearHeading(new Pose2d(30, -45, Math.toRadians(40)))
                .build();

        dropFirstSample = drive.trajectorySequenceBuilder(collectFirstSample.end())
                .lineToLinearHeading(new Pose2d(32, -45, Math.toRadians(-30)))
                .build();

        collectSecondSample = drive.trajectorySequenceBuilder(dropFirstSample.end())
                .lineToLinearHeading(new Pose2d(36, -42, Math.toRadians(35)))
                .build();

        dropSecondSample = drive.trajectorySequenceBuilder(collectSecondSample.end())
                .lineToLinearHeading(new Pose2d(38, -45, Math.toRadians(-30)))
                .build();

        collectThirdSample = drive.trajectorySequenceBuilder(dropSecondSample.end())
                .lineToLinearHeading(new Pose2d(44, -40, Math.toRadians(30)))
                .build();

        dropThirdSample = drive.trajectorySequenceBuilder(collectThirdSample.end())
                .lineToLinearHeading(new Pose2d(38, -45, Math.toRadians(-30)))
                .build();

        goToTakeSecondSpecimen = drive.trajectorySequenceBuilder(dropThirdSample.end())
                .lineToLinearHeading(new Pose2d(42,-60,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(42,-64,Math.toRadians(85)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
                )
                .build();

        hangSecondSpecimen = drive.trajectorySequenceBuilder(goToTakeSecondSpecimen.end())
                .lineToLinearHeading(new Pose2d(2,-30,Math.toRadians(-85)))
                .lineToLinearHeading(new Pose2d(5,-30,Math.toRadians(-90)))
                .build();

        goToTakeThirdSpecimen = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                .lineToLinearHeading(new Pose2d(42,-60.5,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(42,-63.5,Math.toRadians(85)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
                )
                .build();

        hangThirdSpecimen = drive.trajectorySequenceBuilder(goToTakeThirdSpecimen.end())
                .lineToLinearHeading(new Pose2d(2,-30,Math.toRadians(-85)))
                .lineToLinearHeading(new Pose2d(3,-29,Math.toRadians(-90)))
                .build();

        goToTakeFourthSpecimen = drive.trajectorySequenceBuilder(hangThirdSpecimen.end())
                .lineToLinearHeading(new Pose2d(42,-60.5,Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(42,-63.5,Math.toRadians(85)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
                )
                .build();

        hangFourthSpecimen = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                .lineToLinearHeading(new Pose2d(2,-29,Math.toRadians(-90)))
                .lineToLinearHeading(new Pose2d(3,-28,Math.toRadians(-95)))
                .build();

        hangFifthSpecimen = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                .lineToLinearHeading(new Pose2d(45,-55,Math.toRadians(90)))
                .waitSeconds(0.5)
                .lineToLinearHeading(new Pose2d(-4,-33,Math.toRadians(-85)))
                .lineToLinearHeading(new Pose2d(-4,-32,Math.toRadians(-90)))
                .build();

        park = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                //.lineToLinearHeading(new Pose2d(45,-50 ,Math.toRadians(-45)))
                .splineTo(new Vector2d(45,-50),Math.toRadians(-45))
                .build();

    }

    public TrajectorySequence hangFirstSpecimen() {
        return hangFirstSpecimen;
    }

    public TrajectorySequence collectFirstSample() {
        return collectFirstSample;
    }

    public TrajectorySequence dropFirstSample() {
        return dropFirstSample;
    }

    public TrajectorySequence collectSecondSample() {
        return collectSecondSample;
    }

    public TrajectorySequence dropSecondSample() {
        return dropSecondSample;
    }

    public TrajectorySequence collectThirdSample() {
        return collectThirdSample;
    }

    public TrajectorySequence dropThirdSample() {
        return dropThirdSample;
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

    public TrajectorySequence goToTakeFourthSpecimen() {
        return goToTakeFourthSpecimen;
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

//NOTE, Keep this around for speed reduction:
//                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
