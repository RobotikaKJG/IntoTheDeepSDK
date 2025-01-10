package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.Autonomous.Actions.RunSubsystems;
import org.firstinspires.ftc.teamcode.Roadrunner.MecanumDrive;

public class SampleAuton implements Auton {
    ParallelAction autonomous;
    SequentialAction runTrajectories;
    SequentialAction placePreload;
    ParallelAction cycleFirstSample;
    MecanumDrive drive;
    AutonomousActions autonomousActions;

    @Override
    public void start(){
        fillVariables();
    }

    @Override
    public void run(){
        Actions.runBlocking(autonomous);
    }

    private void fillVariables(){
        placePreload = new SequentialAction(

        );

        runTrajectories = new SequentialAction(
                placePreload,
                cycleFirstSample
        );

        autonomous = new ParallelAction(
            autonomousActions.runSubsystems(),
            runTrajectories
        );

    }

}
