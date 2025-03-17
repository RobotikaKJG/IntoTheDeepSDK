//package org.firstinspires.ftc.teamcode.Subsystems.Outtake.DropSampleActions;
//
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;
//
//public class DropSampleControl {
//    private DropSampleStates prevDropSampleStates;
//
//    public void update() {
//        if (prevDropSampleStates != OuttakeStates.getDropSampleState()) {
//            updateStates();
//            prevDropSampleStates = OuttakeStates.getDropSampleState();
//        }
//    }
//
//    private void updateStates() {
//        switch (OuttakeStates.getDropSampleState()) {
//            case raiseSlides:
//                OuttakeStates.setVerticalSlideState(VerticalSlideStates.dropSample);
//                break;
//            case moveArm:
//                OuttakeStates.setArmState(ArmStates.drop);
//                break;
//            case release:
//                OuttakeStates.setSampleClawState(SampleClawStates.halfOpen);
//                break;
//            case moveArmBack:
//                OuttakeStates.setArmState(ArmStates.down);
//                break;
//            case retractSlides:
//                OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
//                break;
//            case idle:
//                break;
//        }
//    }
//}
