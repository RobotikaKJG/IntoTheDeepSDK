package com.example.meepmeeptesting;


import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-35, -61, 1.57))
                        .waitSeconds(2)
                        .splineToLinearHeading(new Pose2d(-48, -35, Math.toRadians(270)), Math.toRadians(90))
                        .back(15)
                        .splineToLinearHeading(new Pose2d(-50, -50, Math.toRadians(-135)), Math.toRadians(240))
                        .waitSeconds(2)
                        .setReversed(true)
                        .splineToLinearHeading(new Pose2d(35, -25 , Math.toRadians(90)), Math.toRadians(90))
                        .setReversed(false)
                        .splineToConstantHeading(new Vector2d(48, -10), Math.toRadians(0))
                        .lineToConstantHeading(new Vector2d(48, -50))
                        .waitSeconds(2)
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}