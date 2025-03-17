//package org.firstinspires.ftc.teamcode.Subsystems.Outtake.DropSampleActions;
//
//import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;
//
//public class DropSampleLogic {
//
//    private final SlideLogic slideLogic;
//    private double currentWait = 0;
//
//    public DropSampleLogic(SlideLogic slideLogic) {
//        this.slideLogic = slideLogic;
//    }
//
//    public void update()
//    {
//        switch(OuttakeStates.getDropSampleState())
//        {
//            case raiseSlides:
//                raiseSlides();
//                break;
//            case waitToRaise:
//                waitToRaise();
//                break;
//            case moveArm:
//                moveArm();
//                break;
//            case release:
//                release();
//                break;
//            case moveArmBack:
//                moveArmBack();
//                break;
//            case retractSlides:
//                retractSlides();
//                break;
//            case idle:
//                idle();
//                break;
//        }
//    }
//
//    private void raiseSlides() {
//        OuttakeStates.setDropSampleState(DropSampleStates.waitToRaise);
//        addWaitTime(OuttakeConstants.raiseWait);
//    }
//
//    private void waitToRaise() {
//        //if (Math.abs(slideLogic.getSlidePosition() - slideLogic.getSlideExtensionTarget()) > OuttakeConstants.sampleDropThreshold) return;
//        if(currentWait > getSeconds()) return;
//        OuttakeStates.setDropSampleState(DropSampleStates.moveArm);
//        addWaitTime(OuttakeConstants.moveArmDownWait);
//    }
//
//    private void moveArm() {
//        if(currentWait > getSeconds()) return;
//
//        addWaitTime(OuttakeConstants.releaseWait);
//        OuttakeStates.setDropSampleState(DropSampleStates.release);
//    }
//
//    private void release() {
//        if(currentWait > getSeconds()) return;
//
//        addWaitTime(OuttakeConstants.moveArmBackWait);
//        OuttakeStates.setDropSampleState(DropSampleStates.moveArmBack);
//    }
//
//    private void moveArmBack() {
//        if(currentWait > getSeconds()) return;
//
//        OuttakeStates.setDropSampleState(DropSampleStates.retractSlides);
//    }
//
//    private void retractSlides() {
//        if(OuttakeStates.getVerticalSlideState() != VerticalSlideStates.closed) return;
//
//        OuttakeStates.setDropSampleState(DropSampleStates.idle);
//    }
//
//    private void idle() {
//
//    }
//
//    private void addWaitTime(double waitTime) {
//        currentWait = getSeconds() + waitTime;
//    }
//
//    private double getSeconds() {
//        return System.currentTimeMillis() / 1_000.0;
//    }
//}
