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
                .lineToLinearHeading(new Pose2d(5, -28,Math.toRadians(-90)),
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
//                        .splineTo(new Vector2d(38,-15),Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(48,-13),Math.toRadians(-90),
                        SampleMecanumDrive.getVelocityConstraint(45, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
//                        .splineToConstantHeading(new Vector2d(42,-10),Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(48,-47,Math.toRadians(90)),Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(45, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
                .splineToSplineHeading(new Pose2d(45,-30,Math.toRadians(90)),Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(45, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
                .splineToConstantHeading(new Vector2d(54,-15),Math.toRadians(-90),
                        SampleMecanumDrive.getVelocityConstraint(45, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
//                        .splineToConstantHeading(new Vector2d(42,-10),Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(55,-48,Math.toRadians(90)),Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(45, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
                .splineToSplineHeading(new Pose2d(52,-30,Math.toRadians(90)),Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(45, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
                .splineToConstantHeading(new Vector2d(61,-15),Math.toRadians(-90),
                        SampleMecanumDrive.getVelocityConstraint(30, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
//                        .splineToConstantHeading(new Vector2d(42,-10),Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(56,-53,Math.toRadians(90)),Math.toRadians(90),
                        SampleMecanumDrive.getVelocityConstraint(45, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )

                .splineToConstantHeading(new Vector2d(56,-64),Math.toRadians(80),
                        SampleMecanumDrive.getVelocityConstraint(20, 1, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,20)
                )
                .build();

        hangSecondSpecimen = drive.trajectorySequenceBuilder(collectFirstSample.end())
//                .lineToLinearHeading(new Pose2d(2,-30,Math.toRadians(-85)))
                .lineToLinearHeading(new Pose2d(3,-27,Math.toRadians(-90)),
                        SampleMecanumDrive.getVelocityConstraint(80, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(40,60)
                )
                .build();

        goToTakeThirdSpecimen = drive.trajectorySequenceBuilder(hangSecondSpecimen.end())
                .lineToLinearHeading(new Pose2d(38,-60,Math.toRadians(90)),
                        SampleMecanumDrive.getVelocityConstraint(80, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(40,60)
                )
                .lineToLinearHeading(new Pose2d(38,-64,Math.toRadians(85)),
                        SampleMecanumDrive.getVelocityConstraint(25, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
                )
                .build();

        hangThirdSpecimen = drive.trajectorySequenceBuilder(goToTakeThirdSpecimen.end())
//                .lineToLinearHeading(new Pose2d(2,-30,Math.toRadians(-85)))
                .lineToLinearHeading(new Pose2d(6,-27,Math.toRadians(-90)),
                        SampleMecanumDrive.getVelocityConstraint(80, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(40,60)
                )
                .build();

        goToTakeFourthSpecimen = drive.trajectorySequenceBuilder(hangThirdSpecimen.end())
                .lineToLinearHeading(new Pose2d(38,-61,Math.toRadians(90)),
                        SampleMecanumDrive.getVelocityConstraint(80, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(40,60)
                )
                .lineToLinearHeading(new Pose2d(38,-64,Math.toRadians(85)),
                        SampleMecanumDrive.getVelocityConstraint(25, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MIN_ACCEL,DriveConstants.MAX_ACCEL)
                )
                .build();

        hangFourthSpecimen = drive.trajectorySequenceBuilder(goToTakeFourthSpecimen.end())
//                .lineToLinearHeading(new Pose2d(2,-29,Math.toRadians(-90)))
                .lineToLinearHeading(new Pose2d(10,-27,Math.toRadians(-90)),
                        SampleMecanumDrive.getVelocityConstraint(80, 6, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(50,80)
                )
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
