package org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class VerticalSlideControl {
    private final SlideLogic slideLogic;
    private VerticalSlideStates prevVerticalSlideStates = VerticalSlideStates.closed;

    public VerticalSlideControl(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void update() {
        if(IntakeStates.getVerticalSlideState() != prevVerticalSlideStates) {
            updateStates();
            prevVerticalSlideStates = IntakeStates.getVerticalSlideState();
        }
//        if(OuttakeStates.getVerticalSlideState() == VerticalSlideStates.closing) {
//            if(slideLogic.slidesBottomReached()) {
//                OuttakeStates.setVerticalSlideState(VerticalSlideStates.closed);
//            }
//            profileRetractionSpeed();
//        }
    }

    private void updateStates() {
        switch(IntakeStates.getVerticalSlideState()){
            case stepUp:
                slideLogic.stepUp();
                IntakeStates.setVerticalSlideState(VerticalSlideStates.extended);
                break;
            case stepDown:
                slideLogic.stepDown();
                IntakeStates.setVerticalSlideState(VerticalSlideStates.extended);
                break;
            case highChamber:
                slideLogic.setSlideExtensionTarget(IntakeConstants.highRungPos);
                break;
        }
    }
}
