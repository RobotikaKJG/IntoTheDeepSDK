package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.ManualClose.ManualCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class LeftBumperControl {
    public LeftBumperControl() {
    }

    public void update() {
        switch(ControlStates.getLeftBumperState()){
            case manualIntakeClose:
                manualIntakeClose();
                break;
            case autoIntakeCloseConfirmation:
                autoIntakeCloseConfirmation();
                break;
            case moveSlidesUp:
                moveSlidesUp();
                break;
            case idle:
                break;
        }
    }

    private void manualIntakeClose() {
        //IntakeStates.setManualCloseStates(ManualCloseStates.activate);
        IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
    }

    private void autoIntakeCloseConfirmation() {
        IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
    }

    private void moveSlidesUp() {
        if(OuttakeStates.getSpecimenClawState() == SpecimenClawStates.closed)
            switch(OuttakeStates.getVerticalSlideState()) {
                case closed:
                    OuttakeStates.setVerticalSlideState(VerticalSlideStates.lowRung);
                    break;
                case lowRung:
                    OuttakeStates.setVerticalSlideState(VerticalSlideStates.highRung);
                    break;
            }
        else
            switch(OuttakeStates.getVerticalSlideState()){
                case closed:
                    OuttakeStates.setVerticalSlideState(VerticalSlideStates.lowBasket);
                    break;
                case lowBasket:
                    OuttakeStates.setVerticalSlideState(VerticalSlideStates.highBasket);
                    break;
        }
    }
}
