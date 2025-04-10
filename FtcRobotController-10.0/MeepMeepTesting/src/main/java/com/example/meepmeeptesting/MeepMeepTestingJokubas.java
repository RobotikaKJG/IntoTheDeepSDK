package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTestingJokubas {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(70, 50, 2, Math.toRadians(180), 11)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(10 , -62, Math.toRadians(-90)))
                        .lineToLinearHeading(new Pose2d(10, -32, Math.toRadians(-90)))
                        .waitSeconds(0.5)

//                        .lineToLinearHeading(new Pose2d(30, -45, Math.toRadians(40)))
//                        .waitSeconds(0.5)
//                        .lineToLinearHeading(new Pose2d(32, -45, Math.toRadians(-35)))
//                        .waitSeconds(0.5)
//                        .lineToLinearHeading(new Pose2d(36, -45, Math.toRadians(40)))
//                        .waitSeconds(0.5)
//                        .lineToLinearHeading(new Pose2d(38, -45, Math.toRadians(-35)))
//                        .waitSeconds(0.5)
//                        .lineToLinearHeading(new Pose2d(42, -45, Math.toRadians(40)))
//                        .waitSeconds(0.5)
//                        .lineToLinearHeading(new Pose2d(38, -45, Math.toRadians(-35)))
//                        .waitSeconds(0.5)

                        .lineToLinearHeading(new Pose2d(48,-45,Math.toRadians(90)))
                        .waitSeconds(2)
                        .lineToLinearHeading(new Pose2d(58,-45,Math.toRadians(90)))
                        .waitSeconds(2)
                        .lineToLinearHeading(new Pose2d(57  ,-45,Math.toRadians(70)))
                        .waitSeconds(2)

                        .lineToLinearHeading(new Pose2d(45,-55,Math.toRadians(90)))
                        .lineToLinearHeading(new Pose2d(0,-33,Math.toRadians(-85)))
                        .lineToLinearHeading(new Pose2d(0,-32,Math.toRadians(-90)))
                        //.setReversed(false)
                        .lineToLinearHeading(new Pose2d(45,-55,Math.toRadians(90)))
                        .lineToLinearHeading(new Pose2d(0,-33,Math.toRadians(-85)))
                        .lineToLinearHeading(new Pose2d(0,-32,Math.toRadians(-90)))
                        //.setReversed(false)
                        .lineToLinearHeading(new Pose2d(45,-55,Math.toRadians(90)))
                        .lineToLinearHeading(new Pose2d(0,-33,Math.toRadians(-85)))
                        .lineToLinearHeading(new Pose2d(0,-32,Math.toRadians(-90)))
                        //.setReversed(false)
                        .lineToLinearHeading(new Pose2d(45,-55,Math.toRadians(90)))
                        .lineToLinearHeading(new Pose2d(0,-33,Math.toRadians(-85)))
                        .lineToLinearHeading(new Pose2d(0,-32,Math.toRadians(-90)))
                        //.setReversed(false)
                        .lineToLinearHeading(new Pose2d(25,-45 ,Math.toRadians(-45)))

//                        .setReversed(false)
//                        .splineTo(new Vector2d(45,-55),Math.toRadians(90))
//                        .setReversed(true)
//                        .splineTo(new Vector2d(0,-38),Math.toRadians(-90))
//                        .splineTo(new Vector2d(0,-32),Math.toRadians(-90))
//                        .splineTo(new Vector2d(45,-55),Math.toRadians(90))
////                        .lineToLinearHeading(new Vector2d(58, -10), Math.toRadians(90))
//                        .waitSeconds(0.5)
//                        .setReversed(true)
//                        .splineTo(new Vector2d(50, -50), Math.toRadians(-90))
////                        .lineToLinearHeading(new Pose2d(50, -50, Math.toRadians(90)))
//                        .waitSeconds(0.5)
//                        .splineToConstantHeading(new Vector2d(42, -20), Math.toRadians(90))
//                        .splineToConstantHeading(new Vector2d(52, -7), Math.toRadians(90))
//                        .lineToLinearHeading(new Pose2d(55, -50, Math.toRadians(90)))
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