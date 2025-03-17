package org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
//import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class ExtendoControl {
    private final SlideLogic slideLogic;
    private ExtendoStates prevExtendoState = ExtendoStates.idle;

    public ExtendoControl(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void update() {
        if(IntakeStates.getExtendoState() != prevExtendoState) {
            updateStates();
            prevExtendoState = IntakeStates.getExtendoState();
        }
        if(IntakeStates.getExtendoState() == ExtendoStates.retracting) {
            if(slideLogic.slidesBottomReached()) {
                IntakeStates.setExtendoState(ExtendoStates.retracted);
            }
        }
    }

    private void updateStates() {
        switch(IntakeStates.getExtendoState()){
            case fullyExtend:
                fullyExtend();
                break;
            case stepDown:
                stepDown();
                break;
            case retracting:
                retracting();
                break;
            case stepUp:
                stepUp();
                break;
            case hold:
                hold();
                break;
            case idle:
                break;
        }
    }

    private void fullyExtend() {
        slideLogic.setSlideExtensionTarget(IntakeConstants.extendoMaxExtension);
        IntakeStates.setExtendoState(ExtendoStates.extended);
    }

    private void stepDown() {
        slideLogic.stepDown();
        if(slideLogic.getSlideExtensionTarget() < IntakeConstants.extendoMinExtension)
        {
            retracting();
            return;
        }
        IntakeStates.setExtendoState(ExtendoStates.extended);
    }

    private void retracting() {
        IntakeStates.setExtendoState(ExtendoStates.retracting);
//        IntakeStates.setEjectionServoState(EjectionServoStates.closed); // to ensure closed, NOTE
        OuttakeStates.setSampleClawState(SampleClawStates.fullyOpen);
        slideLogic.setSlideExtensionTarget(0);
    }

    private void stepUp() {
        if(slideLogic.getSlideExtensionTarget() == 0)
        {
            IntakeStates.setExtendoState(ExtendoStates.extended);
            slideLogic.setSlideExtensionTarget(IntakeConstants.extendoMinExtension);
            return;
        }
        IntakeStates.setExtendoState(ExtendoStates.extended);
        slideLogic.stepUp();
    }

    private void hold(){
        slideLogic.setSlideExtensionTarget(70);
    }
}
