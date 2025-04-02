package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class LeftBumperControl {

    public void update() {
        switch(ButtonStates.getLeftBumperState()){
            case closeIntake:
                closeIntake();
                break;
            case moveSlidesUp:
                moveSlidesUp();
                break;
            case idle:
                break;
        }
    }

    private void closeIntake() {
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
