package org.firstinspires.ftc.teamcode.Autonomous.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.Autonomous.AutonomousConstants;
import org.firstinspires.ftc.teamcode.Enums.PropPos;
import org.firstinspires.ftc.teamcode.Roadrunner.DriveConstants;
import org.firstinspires.ftc.teamcode.Roadrunner.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;

public class BlueBacksideTrajectories implements BacksideTrajectories {

    SampleMecanumDrive drive;
    public TrajectorySequence[] goToProp;
    public TrajectorySequence[] goToYellowPixelPlacement;
    public TrajectorySequence[] parkBackstage;

    int propPosOrdinal;
    private final Pose2d startPose = new Pose2d(9, 59, Math.toRadians(-90));

    public BlueBacksideTrajectories(SampleMecanumDrive drive, PropPos propPos) {
        this.drive = drive;
        fillVariables();
        propPosOrdinal = propPos.ordinal();
    }

    @Override
    public TrajectorySequence goToProp() {
        return goToProp[propPosOrdinal];
    }


    @Override
    public TrajectorySequence goToYellowPixelPlacement() {
        return goToYellowPixelPlacement[propPosOrdinal];
    }

    @Override
    public TrajectorySequence parkBackstage() {
        return parkBackstage[propPosOrdinal];
    }

    @Override
    public Pose2d getStartPose() {
        return startPose;
    }


    private void fillVariables() {

        goToProp = new TrajectorySequence[]{
                drive.trajectorySequenceBuilder(startPose)
                        .lineToSplineHeading(new Pose2d(AutonPositions.BlueBacksideGoToProp0x, AutonPositions.BlueBacksideGoToProp0y, Math.toRadians(180)))
                        .build(),

                drive.trajectorySequenceBuilder(startPose)
                        .lineToSplineHeading(new Pose2d(AutonPositions.BlueBacksideGoToProp1x, AutonPositions.BlueBacksideGoToProp1y, Math.toRadians(180)))
                        .build(),

                drive.trajectorySequenceBuilder(startPose)
                        .lineToSplineHeading(new Pose2d(AutonPositions.BlueBacksideGoToProp2x, AutonPositions.BlueBacksideGoToProp2y, Math.toRadians(180)))
                        .lineToSplineHeading(new Pose2d(AutonPositions.BlueBacksideGoToProp21x, AutonPositions.BlueBacksideGoToProp21y, Math.toRadians(180)))
                        .build()
        };

        goToYellowPixelPlacement = new TrajectorySequence[]{
                drive.trajectorySequenceBuilder(goToProp[0].end())
                        .lineToConstantHeading(new Vector2d(48, 42.5),
                                SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxPixelPlacementSpeed, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                        )
                        .build(),

                drive.trajectorySequenceBuilder(goToProp[1].end())
                        .lineToConstantHeading(new Vector2d(48, 35.5),
                                SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxPixelPlacementSpeed, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                        )
                        .build(),

                drive.trajectorySequenceBuilder(goToProp[2].end())
                        .lineToConstantHeading(new Vector2d(48, 28.5),
                                SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxPixelPlacementSpeed, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                        )
                        .build()
        };

        parkBackstage = new TrajectorySequence[]{
                drive.trajectorySequenceBuilder(goToYellowPixelPlacement[0].end())
                        .lineToConstantHeading(new Vector2d(41, 42.5))
                        .lineToConstantHeading(new Vector2d(41, 60))
                        .lineToConstantHeading(new Vector2d(58, 60),
                                SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxParkSpeed, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                        )
                        .build(),

                drive.trajectorySequenceBuilder(goToYellowPixelPlacement[1].end())
                        .lineToConstantHeading(new Vector2d(41, 35.5))
                        .lineToConstantHeading(new Vector2d(41, 60))
                        .lineToConstantHeading(new Vector2d(58, 60),
                                SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxParkSpeed, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                        )
                        .build(),

                drive.trajectorySequenceBuilder(goToYellowPixelPlacement[2].end())
                        .lineToConstantHeading(new Vector2d(41, 28.5))
                        .lineToConstantHeading(new Vector2d(41, 60))
                        .lineToConstantHeading(new Vector2d(58, 60),
                                SampleMecanumDrive.getVelocityConstraint(AutonomousConstants.maxParkSpeed, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                                SampleMecanumDrive.getAccelerationConstraint(DriveConstants.MAX_ACCEL)
                        )
                        .build()
        };
    }
}
