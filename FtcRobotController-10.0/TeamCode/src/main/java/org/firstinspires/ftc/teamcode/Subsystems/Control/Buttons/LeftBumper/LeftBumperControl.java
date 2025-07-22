package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class LeftBumperControl {
    public LeftBumperControl() {
    }

    public void update() {
        switch(ButtonStates.getLeftBumperState()){
            case toggleIntakeMotorBackward:
                toggleIntakeMotorBackward();
                break;
            case moveSlidesUp:
                moveSlidesUp();
                break;
            case idle:
                break;
        }
    }

    private void toggleIntakeMotorBackward() {
        if(IntakeStates.getMotorState() == IntakeMotorStates.backward)
            IntakeStates.setMotorState(IntakeMotorStates.idleWasBackward);
        else
            IntakeStates.setMotorState(IntakeMotorStates.backward);
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
                case lowBasket:
                    OuttakeStates.setVerticalSlideState(VerticalSlideStates.highBasket);
                    break;
            }
    }
}
