package org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;

public class ArmSlideControl {
    private final SlideLogic slideLogic;
    private ArmSlideStates prevVerticalSlideStates = ArmSlideStates.close;
    public int currentExtentionStep = 0;

    public ArmSlideControl(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void update() {
        if(IntakeStates.getArmSlideState() != prevVerticalSlideStates) {
            updateStates();
            prevVerticalSlideStates = IntakeStates.getArmSlideState();
        }

        if(IntakeStates.getArmSlideState() == ArmSlideStates.closing) {
            if(slideLogic.slidesBottomReached()) {
                IntakeStates.setArmSlideState(ArmSlideStates.closed);
//                IntakeStates.setPivotState(PivotStates.down);
            }
        }
    }

    private void updateStates() {
        switch(IntakeStates.getArmSlideState()){
            case close:
                currentExtentionStep = 0;
                slideLogic.setSlideExtensionTarget(50);
                IntakeStates.setArmSlideState(ArmSlideStates.closing);
//                IntakeStates.setPivotState(PivotStates.upSlightly);
                break;
            case closing:
                break;
            case closed:
                break;
            case extended:
                break;
            case stepUp:
                stepUp();
                break;
            case stepDown:
                stepDown();
                break;
            case fullyExtend:
                fullyExtend();
                break;
        }
    }

    private void stepUp() {
        if(prevVerticalSlideStates == ArmSlideStates.closed) {
            slideLogic.setSlideExtensionTarget(IntakeConstants.slideFirstExtentionStep);
        }
        else {
            slideLogic.stepUp();
        }
        IntakeStates.setArmSlideState(ArmSlideStates.extended);
        currentExtentionStep += 1;
    }

    private void stepDown() {
        if(currentExtentionStep <= 1) {
            IntakeStates.setArmSlideState(ArmSlideStates.close);
        }
        else {
            slideLogic.stepDown();
            IntakeStates.setArmSlideState(ArmSlideStates.extended);
        }

        currentExtentionStep -= 1;
    }
    private void fullyExtend(){
        slideLogic.setSlideExtensionTarget(IntakeConstants.slideMaxExtention);
    }
}
