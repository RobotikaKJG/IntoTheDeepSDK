package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-35, -60, Math.toRadians(0)))
                        .lineToLinearHeading(new Pose2d(-50, -60, Math.toRadians(0)))
                        .waitSeconds(0.5)
                        .lineToLinearHeading(new Pose2d(-48, -48,Math.toRadians(90)))
                        .waitSeconds(0.5)
                        .lineToConstantHeading(new Vector2d(-61, -48))
                        .waitSeconds(2)
//                        .lineToLinearHeading(new Pose2d(-56,-43,Math.toRadians(115)))
//                        .waitSeconds(0.5)
//                        .lineToLinearHeading(new Pose2d(-61, -48,Math.toRadians(90)))
//                        .waitSeconds(0.5)
//                        .splineTo(new Vector2d(-28,-10), Math.toRadians(0))
//                        //.lineToLinearHeading(new Pose2d(-18, -10, Math.toRadians(0)))
//                        .waitSeconds(0.5)
//                        .setReversed(true)
//                        .splineTo(new Vector2d(-58, -45), Math.toRadians(250))
//                        .waitSeconds(0.5)
//                        .setReversed(false)
//                        .splineTo(new Vector2d(-28,-10), Math.toRadians(0))
//                        .waitSeconds(0.5)
//                        .setReversed(true)
//                        .splineTo(new Vector2d(-58, -45), Math.toRadians(250))
//                        .waitSeconds(0.5)
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}