package org.firstinspires.ftc.teamcode.Autonomous.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;

// Abstract class for common trajectories
public interface BacksideTrajectories {

    // Abstract methods for each trajectory sequence to be implemented by subclasses
    TrajectorySequence goToProp();

    TrajectorySequence goToYellowPixelPlacement();

    TrajectorySequence parkBackstage();

    Pose2d getStartPose();
}
