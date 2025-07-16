package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class VerticalSlideControl {
    private final SlideLogic slideLogic;
    private VerticalSlideStates prevVerticalSlideStates = VerticalSlideStates.closed;

    public VerticalSlideControl(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void update() {
        if(OuttakeStates.getVerticalSlideState() != prevVerticalSlideStates) {
            updateStates();
            prevVerticalSlideStates = OuttakeStates.getVerticalSlideState();
        }
//        if(OuttakeStates.getVerticalSlideState() == VerticalSlideStates.closing) {
//            if(slideLogic.slidesBottomReached()) {
//                OuttakeStates.setVerticalSlideState(VerticalSlideStates.closed);
//            }
//            profileRetractionSpeed();
//        }
    }

    private void updateStates() {
        switch(OuttakeStates.getVerticalSlideState()){
            case stepUp:
                slideLogic.stepUp();
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.extended);
                break;
            case stepDown:
                slideLogic.stepDown();
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.extended);
                break;
            case highChamber:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.highRungPos);
                break;
        }
    }
}
