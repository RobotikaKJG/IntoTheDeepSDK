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
                slideLogic.setMaxSpeed(0.8);
                slideLogic.setSlideExtensionTarget(50);
                slideLogic.setMaxSpeed(0.8);
                OuttakeStates.setArmState(ArmStates.down);

                OuttakeStates.setVerticalSlideState(VerticalSlideStates.closing);
                break;
            case dropSample:
//                slideLogic.setSlideExtensionTarget(OuttakeConstants.dropSampleHeight);
                break;
            case lowBasket:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.lowBasketPos);
                break;
            case highBasket:
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
                slideLogic.setMaxSpeed(1);
                slideLogic.setSlideExtensionTarget(OuttakeConstants.hangBar);
                break;
            case highRungScore:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.highRungScorePos);
                break;
            case stepUp:
                slideLogic.stepUp();
                OuttakeStates.setVerticalSlideState(prevVerticalSlideStates);
                break;
            case stepDown:
                slideLogic.stepDown();
                OuttakeStates.setVerticalSlideState(prevVerticalSlideStates);
                break;
        }
    }
    private void profileRetractionSpeed(){
        int position = OuttakeSlideControl.currentPosition;
        //System.out.println("Pos: " + slideLogic.getSlidePosition());
        if(position < OuttakeConstants.profilingThreshold && position > OuttakeConstants.limitSwitchThreshold) {
            //System.out.println("profiling active");
            double slideSpeed = position * OuttakeConstants.speedProfileMultiplier + OuttakeConstants.verticalOffset;
            if(slideSpeed < 0)
                slideSpeed = 0;
            slideLogic.limitSpeed(slideSpeed);
        }
    }
}
