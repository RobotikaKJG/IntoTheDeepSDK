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
    TrajectorySequence dropFirstCollectSecond;
    TrajectorySequence dropSecondCollectThird;
    TrajectorySequence goBack;
    TrajectorySequence hangSecondSpecimen;
    TrajectorySequence goToTakeThirdSpecimen;
    TrajectorySequence hangThirdSpecimen;
    TrajectorySequence goToTakeFourthSpecimen;
    TrajectorySequence hangFourthSpecimen;
    TrajectorySequence goToTakeFifthSpecimen;
    TrajectorySequence hangFifthSpecimen;
    TrajectorySequence park;

    private final Pose2d startPose = new Pose2d(-5, -60,Math.toRadians(90));

    public SpecimenTrajectories(SampleMecanumDrive drive) {
        this.drive = drive;
        fillVariables();
    }

    private void fillVariables() {
        hangFirstSpecimen = drive.trajectorySequenceBuilder(startPose)
//                .lineToLinearHeading(new Pose2d(-6, -26,Math.toRadians(90)),
//                        SampleMecanumDrive.getVelocityConstraint(37, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(60,80)
//                )
                .splineToLinearHeading(new Pose2d(-6, -26, Math.toRadians(90)), Math.toRadians(-90),
                        SampleMecanumDrive.getVelocityConstraint(37, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(60,80)
                )
                .build();

        collectFirstSample = drive.trajectorySequenceBuilder(hangFirstSpecimen.end())
//                .lineToLinearHeading(new Pose2d(49,-48,Math.toRadians(90)))
                .waitSeconds(0.35)
                .lineToLinearHeading(new Pose2d(0,-35, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(38.5, -46, Math.toRadians(60)))
                .build();

        dropFirstCollectSecond = drive.trajectorySequenceBuilder(collectFirstSample.end())
//                .turn(Math.toRadians(-130))
                .lineToLinearHeading(new Pose2d(40, -46, Math.toRadians(-80)))
                .waitSeconds(0.0069)
                .lineToLinearHeading(new Pose2d(36.5, -46, Math.toRadians(83)))
                .build();

        dropSecondCollectThird = drive.trajectorySequenceBuilder(dropFirstCollectSecond.end())
//                .turn(Math.toRadians(-130))
                .lineToLinearHeading(new Pose2d(43, -46, Math.toRadians(-70)))
                .waitSeconds(0.0069)
                .lineToLinearHeading(new Pose2d(45, -41, Math.toRadians(66)))
                .waitSeconds(0.2)
                .build();

        goBack = drive.trajectorySequenceBuilder(dropSecondCollectThird.end())
                .lineToLinearHeading(new Pose2d(45, -45, Math.toRadians(160)),
                    SampleMecanumDrive.getVelocityConstraint(40, 6, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
                .lineToLinearHeading(new Pose2d(33, -55, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(33, -65, Math.toRadians(90)))
                .build();

//        hangSecondSpecimen = drive.trajectorySequenceBuilder(new Pose2d(50,-64,Math.toRadians(90)))
        hangSecondSpecimen = drive.trajectorySequenceBuilder(goBack.end())
//                .lineToLinearHeading(new Pose2d(33,-60,Math.toRadians(90)),
//                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(50,80))
                .lineToLinearHeading(new Pose2d(-5,-25,Math.toRadians(90)),
                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
                .build();

        goToTakeThirdSpecimen = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                .waitSeconds(0.2)
//                .lineToLinearHeading(new Pose2d(33,-56,Math.toRadians(90)),
//                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(50,80)
//                )
                .lineToLinearHeading(new Pose2d(36,-55,Math.toRadians(85)),
                        SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
                )
                .splineToLinearHeading(new Pose2d(36, -63, Math.toRadians(90)), Math.toRadians(-90),
                        SampleMecanumDrive.getVelocityConstraint(50, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(60,80)
                )
//                .lineToLinearHeading(new Pose2d(41,-57.5,Math.toRadians(90)),
//                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(50,80)
//                )
                .build();

        hangThirdSpecimen = drive.trajectorySequenceBuilder(goToTakeThirdSpecimen.end())
                .lineToLinearHeading(new Pose2d(-4,-25,Math.toRadians(90)),
                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
                .build();

        goToTakeFourthSpecimen = drive.trajectorySequenceBuilder(hangThirdSpecimen.end())
                .waitSeconds(0.1)
//                .lineToLinearHeading(new Pose2d(33,-55,Math.toRadians(90)),
//                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(50,80)
//                )
                .lineToLinearHeading(new Pose2d(36,-55,Math.toRadians(85)),
                        SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
                )
                .splineToLinearHeading(new Pose2d(36, -63, Math.toRadians(90)), Math.toRadians(-90),
                        SampleMecanumDrive.getVelocityConstraint(50, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(60,80)
                )
//                .lineToLinearHeading(new Pose2d(41,-57.5,Math.toRadians(85)),
//                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(50,80)
//                )
                .build();

        hangFourthSpecimen = drive.trajectorySequenceBuilder(goToTakeFourthSpecimen.end())
                .lineToLinearHeading(new Pose2d(-1,-25,Math.toRadians(90)),
                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
                .build();

        goToTakeFifthSpecimen = drive.trajectorySequenceBuilder(hangFourthSpecimen.end())
                .waitSeconds(0.1)
//                .lineToLinearHeading(new Pose2d(33,-55,Math.toRadians(90)),
//                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(50,80)
//                )
                .lineToLinearHeading(new Pose2d(36,-55,Math.toRadians(85)),
                        SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
                )
                .splineToLinearHeading(new Pose2d(36, -63, Math.toRadians(90)), Math.toRadians(-90),
                        SampleMecanumDrive.getVelocityConstraint(50, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(60,80)
                )
//                .lineToLinearHeading(new Pose2d(41,-58,Math.toRadians(80)),
//                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
//                        SampleMecanumDrive.getAccelerationConstraint(50,80)
//                )
                .build();

        hangFifthSpecimen = drive.trajectorySequenceBuilder(goToTakeFifthSpecimen.end())
                .lineToLinearHeading(new Pose2d(1.5,-25,Math.toRadians(90)),
                        SampleMecanumDrive.getVelocityConstraint(100, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
                .build();

        park = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
//                .splineTo(new Vector2d(45,-50),Math.toRadians(-45))
                .splineTo(new Vector2d(3,-27),Math.toRadians(90))
                .build();

    }

    public TrajectorySequence hangFirstSpecimen() {
        return hangFirstSpecimen;
    }

    public TrajectorySequence collectFirstSample() {
        return collectFirstSample;
    }

    public TrajectorySequence dropFirstCollectSecond() { return dropFirstCollectSecond;}

    public TrajectorySequence dropSecondCollectThird() { return dropSecondCollectThird;}

    public TrajectorySequence goBack() { return goBack;}

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