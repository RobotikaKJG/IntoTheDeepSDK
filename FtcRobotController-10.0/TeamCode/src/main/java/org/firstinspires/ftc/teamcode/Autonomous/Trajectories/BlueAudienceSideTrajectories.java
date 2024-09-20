package org.firstinspires.ftc.teamcode.Autonomous.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.Autonomous.AutonomousConstants;
import org.firstinspires.ftc.teamcode.Enums.PropPos;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Roadrunner.DriveConstants;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;

public class BlueAudienceSideTrajectories implements AudienceSideTrajectories {

    SampleMecanumDrive drive;
    public TrajectorySequence[] goToPixelStack;
    public TrajectorySequence[] goBackstage;
    public TrajectorySequence[] goToYellowPixelPlacement;
    public TrajectorySequence goBackToStack;
    public TrajectorySequence goBackToBackstage;
    public TrajectorySequence parkBackstage;

    int propPosOrdinal;
    private final Pose2d startPose = new Pose2d(-33, 63, Math.toRadians(90));

    public BlueAudienceSideTrajectories(SampleMecanumDrive drive, PropPos propPos) {
        this.drive = drive;
        fillVariables();
        propPosOrdinal = propPos.ordinal();
    }

    @Override
    public TrajectorySequence goToPixelStack() {
        return goToPixelStack[propPosOrdinal];
    }

    @Override
    public TrajectorySequence goBackstage() {
        return goBackstage[propPosOrdinal];
    }

    @Override
    public TrajectorySequence goToYellowPixelPlacement() {
        return goToYellowPixelPlacement[propPosOrdinal];
    }

    @Override
    public TrajectorySequence goBackToStack() {
        return goBackToStack;
    }

    @Override
    public TrajectorySequence goBackToBackstage() {
        return goBackToBackstage;
    }

    @Override
    public TrajectorySequence parkBackstage() {
        return parkBackstage;
    }

    @Override
    public Pose2d getStartPose() {
        return startPose;
    }


    private void fillVariables() {



        goToPixelStack = new TrajectorySequence[]{
                drive.trajectorySequenceBuilder(startPose)
                        .lineToLinearHeading(new Pose2d(-40, 36, Math.toRadians(0))) // needed to not hit the truss
                        .lineTo(new Vector2d(-33.5, 32))
                        .splineToLinearHeading(new Pose2d(-55, 24, Math.toRadians(170)), Math.toRadians(0),
                                SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxStackHitSpeed, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                        )
                        .lineToSplineHeading(new Pose2d(-54, 15,Math.toRadians(180)))
                        .build(),

                drive.trajectorySequenceBuilder(startPose)
                        .setReversed(true)
                        .lineToLinearHeading(new Pose2d(-44, 23.5, Math.toRadians(0)))
                        .lineToConstantHeading(new Vector2d(-48, 23.5))
                        .setReversed(false)
                        .splineToLinearHeading(new Pose2d(-56, 25, Math.toRadians(170)), Math.toRadians(180),
                                SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxStackHitSpeed, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                        )
                        .lineToSplineHeading(new Pose2d(-55, 15,Math.toRadians(180)))
                        .build(),

                drive.trajectorySequenceBuilder(startPose)
                        .setReversed(true)
                        .splineTo(new Vector2d(-51, 17),Math.toRadians(-110))
                        .setReversed(false)
                        .splineToLinearHeading(new Pose2d(-56, 24, Math.toRadians(180)), Math.toRadians(0),
                                SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxStackHitSpeed, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                        )
                        .lineToSplineHeading(new Pose2d(-55, 12,Math.toRadians(180)))
                        .build()
        };

        goBackstage = new TrajectorySequence[]{
                drive.trajectorySequenceBuilder(goToPixelStack[0].end()) // all end at the same spot
                        .setReversed(true)
                        .splineTo(new Vector2d(-35, 10.3), Math.toRadians(0))
                        .splineTo(new Vector2d(0, 10.3), Math.toRadians(0))
                        .splineTo(new Vector2d(24, 11), Math.toRadians(0))
//                        .splineTo(new Vector2d(46, 40), Math.toRadians(0))
                        .splineTo(new Vector2d(50, 36), Math.toRadians(0),
                                SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxPixelPlacementSpeed, AutonomousConstants.maxAngularPixelPlacementSpeed, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                        )
                        .build(),
                drive.trajectorySequenceBuilder(goToPixelStack[1].end())
                        .setReversed(true)
                        .splineTo(new Vector2d(-35, 11), Math.toRadians(0))
                        .splineTo(new Vector2d(0, 11), Math.toRadians(0))
                        .splineTo(new Vector2d(20, 11), Math.toRadians(0))
//                        .splineTo(new Vector2d(43, 36), Math.toRadians(0))
                        .splineTo(new Vector2d(51, 31), Math.toRadians(0),
                                SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxPixelPlacementSpeed, AutonomousConstants.maxAngularPixelPlacementSpeed, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                        )
                        .build(),
                drive.trajectorySequenceBuilder(goToPixelStack[2].end())
                        .setReversed(true)
                        .splineTo(new Vector2d(-35, 10), Math.toRadians(0))
                        .splineTo(new Vector2d(0, 10), Math.toRadians(0))
                        .splineTo(new Vector2d(10, 10), Math.toRadians(0))
//                        .splineTo(new Vector2d(43, 40), Math.toRadians(0))
                        .splineTo(new Vector2d(51, 38), Math.toRadians(0),
                                SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxPixelPlacementSpeed, AutonomousConstants.maxAngularPixelPlacementSpeed, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                        )
                        .build()
        };

        goToYellowPixelPlacement = new TrajectorySequence[]{
                drive.trajectorySequenceBuilder(goBackstage[0].end())
                        .lineToConstantHeading(new Vector2d(51, 43.5))
                        .waitSeconds(0.5)
                        .build(),

                drive.trajectorySequenceBuilder(goBackstage[1].end())
                        .lineToConstantHeading(new Vector2d(52, 38)) //-37,5
                        .waitSeconds(0.3)
                        .build(),

                drive.trajectorySequenceBuilder(goBackstage[2].end())
                        .lineToConstantHeading(new Vector2d(51.5, 29))
                        //.waitSeconds(3)
                        .build()
        };

        goBackToStack = drive.trajectorySequenceBuilder(adjustPosition())
                .splineToConstantHeading(new Vector2d(45.5, 31),Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(23.5, 6.5),Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-51.5, 6.5),Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-58, 17),Math.toRadians(180),
                        SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxPixelTakeSpeed, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .splineToConstantHeading(new Vector2d(-57, 8),Math.toRadians(180),
                        SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxPixelTakeSpeed, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .build();
        goBackToBackstage = drive.trajectorySequenceBuilder(goBackToStack.end())
                .setReversed(true)
                .splineTo(new Vector2d(-21.5, 9.5), Math.toRadians(0))
                .splineTo(new Vector2d(18.5, 9.5), Math.toRadians(0))
//                .splineTo(new Vector2d(43, -32), Math.toRadians(0))
                .splineTo(new Vector2d(51.5, 32), Math.toRadians(0),
                        SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxPixelPlacementSpeed, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                        SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                )
                .build();

        parkBackstage = drive.trajectorySequenceBuilder(goBackToBackstage.end())
                .lineToConstantHeading(new Vector2d(45, 30))
                .build();
    }

    @Override
    public Pose2d adjustPosition()
    {
        switch (GlobalVariables.propPos) {
            case left:
                return new Pose2d(51, 46,Math.toRadians(183));
            case front:
                return new Pose2d(52, 38,Math.toRadians(178.5));//(-177.5));
            case right:
                return new Pose2d(53, 25,Math.toRadians(-179));
        }
        return null;
    }
}
