package org.firstinspires.ftc.teamcode.Subsystems.Intake.Old.OldNew;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeRotationStates;

public class IntakeStateSetter {

    private IntakeStateCombinations prevIntakeStateCombinations;

    public void update(){
//        if (prevIntakeStateCombinations == IntakeStates.getIntakeStateCombinations()) return;
//
//        setStates(IntakeStates.getIntakeStateCombinations());
//        prevIntakeStateCombinations = IntakeStates.getIntakeStateCombinations();
    }

    public void setStates(IntakeStateCombinations intakeStateCombinations) {
        switch (intakeStateCombinations) {
            case retractedIntaking:
                retractedIntaking();
                break;
            case retractedExtraEjecting:
                retractedExtraEjecting();
                break;
            case retractedAllEjecting:
                retractedAllEjecting();
                break;
            case retractedSampleSecuring:
                retractedSampleSecuring();
                break;
            case retractedSampleSecuringWhileEjecting:
                retractedSampleSecuringWhileEjecting();
                break;
            case extendedIntaking:
                extendedIntaking();
                break;
            case extendedExtraEjecting:
                extendedExtraEjecting();
                break;
            case extendedAllEjecting:
                extendedAllEjecting();
                break;
            case extendedSampleSecuring:
                extendedSampleSecuring();
                break;
            case extendedSampleSecuringWhileEjecting:
                extendedSampleSecuringWhileEjecting();
                break;
            case idle:
                idle();
                break;
        }
    }

    private void retractedIntaking() {
        IntakeStates.setMotorState(IntakeRotationStates.forward);
        IntakeStates.setServoState(IntakeRotationStates.forward);
        IntakeStates.setExtendoState(ExtendoStates.retracted);
    }

    private void retractedExtraEjecting() {
        IntakeStates.setMotorState(IntakeRotationStates.backward);
        IntakeStates.setServoState(IntakeRotationStates.idle);
        IntakeStates.setExtendoState(ExtendoStates.retracted);
    }

    private void retractedAllEjecting() {
        IntakeStates.setMotorState(IntakeRotationStates.backward);
        IntakeStates.setServoState(IntakeRotationStates.backward);
        IntakeStates.setExtendoState(ExtendoStates.retracted);
    }

    private void retractedSampleSecuring() {
        IntakeStates.setMotorState(IntakeRotationStates.idleWasForward);
        IntakeStates.setServoState(IntakeRotationStates.forward);
        IntakeStates.setExtendoState(ExtendoStates.retracted);
    }

    private void retractedSampleSecuringWhileEjecting() {
        IntakeStates.setMotorState(IntakeRotationStates.backward);
        IntakeStates.setServoState(IntakeRotationStates.forward);
        IntakeStates.setExtendoState(ExtendoStates.retracted);
    }

    private void extendedIntaking() {
        IntakeStates.setMotorState(IntakeRotationStates.forward);
        IntakeStates.setServoState(IntakeRotationStates.forward);
        IntakeStates.setExtendoState(ExtendoStates.extended);
    }

    private void extendedExtraEjecting() {
        IntakeStates.setMotorState(IntakeRotationStates.backward);
        IntakeStates.setServoState(IntakeRotationStates.idle);
        IntakeStates.setExtendoState(ExtendoStates.extended);
    }

    private void extendedAllEjecting() {
        IntakeStates.setMotorState(IntakeRotationStates.backward);
        IntakeStates.setServoState(IntakeRotationStates.backward);
        IntakeStates.setExtendoState(ExtendoStates.extended);
    }

    private void extendedSampleSecuring() {
        IntakeStates.setMotorState(IntakeRotationStates.idleWasForward);
        IntakeStates.setServoState(IntakeRotationStates.forward);
        IntakeStates.setExtendoState(ExtendoStates.extended);
    }

    private void extendedSampleSecuringWhileEjecting() {
        IntakeStates.setMotorState(IntakeRotationStates.backward);
        IntakeStates.setServoState(IntakeRotationStates.forward);
        IntakeStates.setExtendoState(ExtendoStates.extended);
    }

    private void idle() {
        IntakeStates.setMotorState(IntakeRotationStates.idle);
        IntakeStates.setServoState(IntakeRotationStates.idle);
        IntakeStates.setExtendoState(ExtendoStates.idle);
    }
}
