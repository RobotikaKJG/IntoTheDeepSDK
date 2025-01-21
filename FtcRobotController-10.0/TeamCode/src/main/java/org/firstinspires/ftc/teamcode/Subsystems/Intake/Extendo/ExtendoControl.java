package org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
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
            case stepDown:
                stepDown();
                break;
            case retracting:
                retracting();
                break;
            case stepUp:
                stepUp();
                break;
            case idle:
                break;
        }
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
        IntakeStates.setEjectionServoState(EjectionServoStates.closed); // to ensure closed, NOTE
        OuttakeStates.setClawState(ClawStates.fullyOpen);
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
}
