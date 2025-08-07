package org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Main.Dependencies;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class ArmSlideControl {
    private final SlideLogic slideLogic;
    private ArmSlideStates prevVerticalSlideStates = ArmSlideStates.closed;
    public int currentExtentionStep = 0;

    public ArmSlideControl(SlideLogic slideLogic) {
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
                stepUp();
                break;
            case stepDown:
                stepDown();
                break;
            case closed:
                break;
        }
    }

    private void stepUp() {
        if(prevVerticalSlideStates == ArmSlideStates.closed) {
            slideLogic.stepUpMore();
        }
        else {
            slideLogic.stepUp();
        }
        IntakeStates.setVerticalSlideState(ArmSlideStates.extended);
        currentExtentionStep += 1;
    }

    private void stepDown() {
        if(currentExtentionStep <= 1) {
            slideLogic.stepDownMore();
            IntakeStates.setVerticalSlideState(ArmSlideStates.closed);
        }
        else {
            slideLogic.stepDown();
            IntakeStates.setVerticalSlideState(ArmSlideStates.extended);
        }

        currentExtentionStep -= 1;
    }
}
