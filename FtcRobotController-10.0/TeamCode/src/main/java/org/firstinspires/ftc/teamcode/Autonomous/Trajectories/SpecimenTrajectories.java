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
    TrajectorySequence hangSecondSpecimen;
    TrajectorySequence goToTakeThirdSpecimen;
    TrajectorySequence hangThirdSpecimen;
    TrajectorySequence goToTakeFourthSpecimen;
    TrajectorySequence hangFourthSpecimen;
    TrajectorySequence goToTakeFifthSpecimen;
    TrajectorySequence hangFifthSpecimen;
    TrajectorySequence park;

    private final Pose2d startPose = new Pose2d(7, -60,Math.toRadians(-90));

    public SpecimenTrajectories(SampleMecanumDrive drive) {
        this.drive = drive;
        fillVariables();
    }

    private void fillVariables() {
        hangFirstSpecimen = drive.trajectorySequenceBuilder(startPose)
                .lineToLinearHeading(new Pose2d(0, -28,Math.toRadians(-90)),
                        SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(60,80)
                )
                .build();

        collectFirstSample = drive.trajectorySequenceBuilder(hangFirstSpecimen.end())
//                .lineToLinearHeading(new Pose2d(49,-48,Math.toRadians(90)))

                .splineTo(new Vector2d(34,-40),Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL, 4, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
                )
                .splineToConstantHeading(new Vector2d(49,-18),Math.toRadians(-90),
                        SampleMecanumDrive.getVelocityConstraint(45, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
                .splineToSplineHeading(new Pose2d(48,-43,Math.toRadians(90)),Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(45, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
                .splineToSplineHeading(new Pose2d(45,-25,Math.toRadians(90)),Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(45, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
                .splineToConstantHeading(new Vector2d(55,-17),Math.toRadians(-90),
                        SampleMecanumDrive.getVelocityConstraint(25, 1.5, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
                .splineToSplineHeading(new Pose2d(55,-48.5,Math.toRadians(90)),Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(50, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,25)
                )
                .splineToSplineHeading(new Pose2d(50,-25,Math.toRadians(90)),Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(45, 1.5, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
                .splineToConstantHeading(new Vector2d(60.5,-15),Math.toRadians(-90),
                        SampleMecanumDrive.getVelocityConstraint(25, 1.5, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
                .splineToSplineHeading(new Pose2d(54,-57,Math.toRadians(80)),Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(40, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )

                .splineToConstantHeading(new Vector2d(54,-65),Math.toRadians(80),
                        SampleMecanumDrive.getVelocityConstraint(20, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(10,15)
                )
                .build();

//        hangSecondSpecimen = drive.trajectorySequenceBuilder(new Pose2d(50,-64,Math.toRadians(90)))
        hangSecondSpecimen = drive.trajectorySequenceBuilder(collectFirstSample.end())
                .lineToLinearHeading(new Pose2d(0,-27,Math.toRadians(-90)),
                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
                .build();

        goToTakeThirdSpecimen = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                .lineToLinearHeading(new Pose2d(38,-54,Math.toRadians(90)),
                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
                .lineToLinearHeading(new Pose2d(38,-66,Math.toRadians(85)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
                )
//                .lineToLinearHeading(new Pose2d(41,-57.5,Math.toRadians(90)),
//                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(50,80)
//                )
                .build();

        hangThirdSpecimen = drive.trajectorySequenceBuilder(goToTakeThirdSpecimen.end())
                .lineToLinearHeading(new Pose2d(4,-27,Math.toRadians(-90)),
                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
                .build();

        goToTakeFourthSpecimen = drive.trajectorySequenceBuilder(hangThirdSpecimen.end())
                .lineToLinearHeading(new Pose2d(40,-50,Math.toRadians(90)),
                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
                .lineToLinearHeading(new Pose2d(40,-66.5,Math.toRadians(75)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
                )
//                .lineToLinearHeading(new Pose2d(41,-57.5,Math.toRadians(85)),
//                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(50,80)
//                )
                .build();

        hangFourthSpecimen = drive.trajectorySequenceBuilder(goToTakeFourthSpecimen.end())
                .lineToLinearHeading(new Pose2d(7,-27,Math.toRadians(-90)),
                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
                .build();

        goToTakeFifthSpecimen = drive.trajectorySequenceBuilder(hangFourthSpecimen.end())
                .lineToLinearHeading(new Pose2d(38,-52,Math.toRadians(90)),
                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
                .lineToLinearHeading(new Pose2d(38,-63,Math.toRadians(85)),
                        SampleMecanumDrive.getVelocityConstraint(30, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
                )
//                .lineToLinearHeading(new Pose2d(41,-58,Math.toRadians(80)),
//                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(50,80)
//                )
                .build();

        hangFifthSpecimen = drive.trajectorySequenceBuilder(goToTakeFifthSpecimen.end())
                .lineToLinearHeading(new Pose2d(10,-27,Math.toRadians(-90)),
                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
                .build();

        park = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                .splineTo(new Vector2d(45,-50),Math.toRadians(-45))
                .build();

    }

    public TrajectorySequence hangFirstSpecimen() {
        return hangFirstSpecimen;
    }

    public TrajectorySequence collectFirstSample() {
        return collectFirstSample;
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

    public TrajectorySequence goToTakeFifthSpecimen() {
        return goToTakeFifthSpecimen;
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

//collectFirstSample = drive.trajectorySequenceBuilder(hangFirstSpecimen.end())
//        .splineTo(new Vector2d(34,-40),Math.toRadians(90),
//                        SampleMecanumDrive.getVelocityConstraint(DriveConstants.MAX_VEL, 4, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
//                )
//                        .splineToConstantHeading(new Vector2d(47,-15),Math.toRadians(-90),
//                        SampleMecanumDrive.getVelocityConstraint(55, 1, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,25)
//                )
//                        .splineToSplineHeading(new Pose2d(50,-42,Math.toRadians(90)),Math.toRadians(90),
//                        SampleMecanumDrive.getVelocityConstraint(55, 1, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,25)
//                )
//                        .splineToSplineHeading(new Pose2d(43,-30,Math.toRadians(90)),Math.toRadians(90),
//                        SampleMecanumDrive.getVelocityConstraint(35, 1.5, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,25)
//                )
//                        .splineToConstantHeading(new Vector2d(54,-15),Math.toRadians(-90),
//                        SampleMecanumDrive.getVelocityConstraint(35, 1, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,25)
//                )
//                        .splineToSplineHeading(new Pose2d(55,-45,Math.toRadians(90)),Math.toRadians(90),
//                        SampleMecanumDrive.getVelocityConstraint(55, 1, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,25)
//                )
//                        .splineToSplineHeading(new Pose2d(50,-30,Math.toRadians(90)),Math.toRadians(90),
//                        SampleMecanumDrive.getVelocityConstraint(35, 1.5, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,25)
//                )
//                        .splineToConstantHeading(new Vector2d(60,-16),Math.toRadians(-90),
//                        SampleMecanumDrive.getVelocityConstraint(35, 1, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,25)
//                )
//                        .splineToSplineHeading(new Pose2d(56,-53,Math.toRadians(90)),Math.toRadians(90),
//                        SampleMecanumDrive.getVelocityConstraint(45, 1, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,25)
//                )
//
//                        .splineToConstantHeading(new Vector2d(56,-64),Math.toRadians(80),
//                        SampleMecanumDrive.getVelocityConstraint(20, 1, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
//                )
//                        .build();