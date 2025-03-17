//package org.firstinspires.ftc.teamcode.Subsystems.Outtake.TakeSpecimen;
//
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;
//import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
//
//public class TakeSpecimenControl {
//    private TakeSpecimenStates prevTakeSpecimenStates;
//
//    public void update() {
//        if (prevTakeSpecimenStates != OuttakeStates.getTakeSpecimenStates()) {
//            updateStates();
//            prevTakeSpecimenStates = OuttakeStates.getTakeSpecimenStates();
//        }
//    }
//
//    private void updateStates(){
//        switch (OuttakeStates.getTakeSpecimenStates())
//        {
//            case takeSpecimen:
//                OuttakeStates.setSpecimenClawState(SpecimenClawStates.closed);
//                break;
//            case raiseClaw:
//                OuttakeStates.setVerticalSlideState(VerticalSlideStates.lowRung);
//                break;
//            case idle:
//                break;
//        }
//    }
//}
