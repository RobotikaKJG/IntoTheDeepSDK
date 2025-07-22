package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

import java.util.Objects;

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
            case openClaw:
                openClaw();
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
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.highRung);
    }

    private void openClaw() {
        OuttakeStates.setClawState(ClawStates.fullyOpen);
    }
}
