package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class   MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(68, 40.5, 6.9, Math.toRadians(180), 11)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(7, -61,Math.toRadians(-90)))
                        .lineToLinearHeading(new Pose2d(10, -29,Math.toRadians(-90)))
                        .splineToConstantHeading(new Vector2d(35, -30), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(35, -25), Math.toRadians(90))
                        .splineToConstantHeading(new Vector2d(40, -5), Math.toRadians(0))
                        .splineToConstantHeading(new Vector2d(49, -10), Math.toRadians(-90))
                        .splineToConstantHeading(new Vector2d(49, -35), Math.toRadians(-90))
                        .lineToLinearHeading(new Pose2d(48,-57,Math.toRadians(90)))
                        .lineToLinearHeading(new Pose2d(48,-60,Math.toRadians(90)))
                        .lineToLinearHeading(new Pose2d(5,-33,Math.toRadians(-85)))
                        .lineToLinearHeading(new Pose2d(5,-30,Math.toRadians(-90)))
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}

//                        .lineToLinearHeading(new Pose2d(-52.75, -52.75, Math.toRadians(45)))
//        .waitSeconds(2)
//                        .lineToLinearHeading(new Pose2d(-50, -48,Math.toRadians(90)))
//        .waitSeconds(3)
//                        .lineToLinearHeading(new Pose2d(-54.5, -51, Math.toRadians(35)))
//        .waitSeconds(3)
//                        .lineToLinearHeading(new Pose2d(-60,-48,Math.toRadians(95)))
//        .waitSeconds(3)
//                        .lineToLinearHeading(new Pose2d(-52, -52.5, Math.toRadians(45)))
//        .waitSeconds(4)
//                        .lineToLinearHeading(new Pose2d(-55,-47, Math.toRadians(130)))
//        .waitSeconds(3)
//                        .lineToLinearHeading(new Pose2d(-52, -52.5, Math.toRadians(45)))
////                        .lineToLinearHeading(new Pose2d(-55, -55, Math.toRadians(45)))
////                        .waitSeconds(0.5)
////                        .lineToLinearHeading(new Pose2d(-48, -48,Math.toRadians(90)))
////                        .waitSeconds(0.5)
////                        .lineToLinearHeading(new Pose2d(-55, -55, Math.toRadians(45)))
////                        .waitSeconds(2)
////                        .lineToLinearHeading(new Pose2d(-60,-48,Math.toRadians(90)))
////                        .waitSeconds(0.5)
////                        .lineToLinearHeading(new Pose2d(-55, -55, Math.toRadians(45)))
////                        .waitSeconds(0.5)
////                        .lineToLinearHeading(new Pose2d(-55,-47, Math.toRadians(135)))
//////                        .splineTo(new Vector2d(-28,-10), Math.toRadians(0))
//////                        //.lineToLinearHeading(new Pose2d(-18, -10, Math.toRadians(0)))
////                        .waitSeconds(0.5)
//////                        .setReversed(true)
//////                        .splineTo(new Vector2d(-58, -45), Math.toRadians(250))
//////                        .waitSeconds(0.5)
//////                        .setReversed(false)
//////                        .splineTo(new Vector2d(-28,-10), Math.toRadians(0))
//////                        .waitSeconds(0.5)
//////                        .setReversed(true)
//////                        .splineTo(new Vector2d(-58, -45), Math.toRadians(250))
//////                        .waitSeconds(0.5)