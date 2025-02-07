package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;

import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class LeftBumperLogic {
    private final LeftBumperControl leftBumperControl = new LeftBumperControl();
    
    public void update()
    {
        if(manualIntakeClose()) return;
        if(autoIntakeCloseConfirmation()) return;
        if(moveSlidesUp()) return;
    }

    private void completeAction(){
        leftBumperControl.update();
        ButtonStates.setLeftBumperState(LeftBumperStates.idle);
    }

    private boolean manualIntakeClose() {
        if(intakeClosing()) return false;

        ButtonStates.setLeftBumperState(LeftBumperStates.manualIntakeClose);
        completeAction();
        return true;
    }

    private boolean intakeActive(){
        return IntakeStates.getIntakeState() != SubsystemState.Idle;
    }

    private boolean autoIntakeCloseConfirmation() {
        if(IntakeStates.getAutoCloseStates() != AutoCloseStates.waitForCommand) return false;

        ButtonStates.setLeftBumperState(LeftBumperStates.autoIntakeCloseConfirmation);
        completeAction();
        return true;
    }

    private boolean moveSlidesUp() {
        if(intakeActive()) return false;

        ButtonStates.setLeftBumperState(LeftBumperStates.moveSlidesUp);
        completeAction();
        return true;
    }

    private boolean intakeClosing(){
        return IntakeStates.getAutoCloseStates() == AutoCloseStates.waitForCommand ||
                IntakeStates.getAutoCloseStates() == AutoCloseStates.waitToRetract ||
                IntakeStates.getAutoCloseStates() == AutoCloseStates.closeSampleClaw ||
                IntakeStates.getAutoCloseStates() == AutoCloseStates.idle;
    }
}
