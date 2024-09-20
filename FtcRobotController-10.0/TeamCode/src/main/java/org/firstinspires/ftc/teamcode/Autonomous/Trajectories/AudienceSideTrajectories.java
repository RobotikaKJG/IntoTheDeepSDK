package org.firstinspires.ftc.teamcode.Autonomous.Trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.Roadrunner.trajectorysequence.TrajectorySequence;

public interface AudienceSideTrajectories {

    // Abstract methods for each trajectory sequence to be implemented by subclasses
    TrajectorySequence goToPixelStack();

    TrajectorySequence goBackstage();

    TrajectorySequence goToYellowPixelPlacement();
    TrajectorySequence goBackToStack();
    TrajectorySequence goBackToBackstage();

    TrajectorySequence parkBackstage();

    Pose2d getStartPose();

    Pose2d adjustPosition();
}
