package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;

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
        if(OuttakeStates.getVerticalSlideState() == VerticalSlideStates.closing) {
            if(slideLogic.slidesBottomReached()) {
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.closed);
                OuttakeStates.setSampleReleaseButtonState(SampleReleaseButtonStates.idle);
            }
            profileRetractionSpeed();
        }
    }

    private void updateStates() {
        switch(OuttakeStates.getVerticalSlideState()){
            case close:
                slideLogic.setSlideExtensionTarget(20);
                OuttakeStates.setArmState(ArmStates.down);
                //OuttakeStates.setReleaseButtonState(ReleaseButtonStates.idle);
//                if(slideLogic.getSlidePosition() <= 150) {slideLogic.setMaxSpeed(0.1);}
//                slideLogic.setMaxSpeed(0.1);
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.closing);
                break;
            case dropSample:
//                slideLogic.setSlideExtensionTarget(OuttakeConstants.dropSampleHeight);
                break;
            case lowBasket:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.lowBasketPos);
                break;
            case highBasket:
                slideLogic.setMaxSpeed(0.1);
                slideLogic.setSlideExtensionTarget(OuttakeConstants.highBasketPos);
                break;
            case lowRung:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.lowRungPos);
                break;
            case lowRungScore:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.lowRungScorePos);
                break;
            case highRung:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.highRungPos);
                break;
            case hang:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.hangBar);
                break;
            case highRungScore:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.highRungScorePos);
                break;
        }
    }
    private void profileRetractionSpeed(){
        int position = slideLogic.getSlidePosition();
        if(position < OuttakeConstants.profilingThreshold) {
            double slideSpeed = position * OuttakeConstants.speedProfileMultiplier;
            slideLogic.limitSpeed(slideSpeed);
        }
    }
}
