package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

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
        ControlStates.setLeftBumperState(LeftBumperStates.idle);
    }

    private boolean manualIntakeClose() {
        if(!intakeActive() || IntakeStates.getAutoCloseStates() == AutoCloseStates.waitForCommand) return false;

        ControlStates.setLeftBumperState(LeftBumperStates.manualIntakeClose);
        completeAction();
        return true;
    }

    private boolean intakeActive(){
        return IntakeStates.getIntakeState() != SubsystemState.Idle;
    }

    private boolean autoIntakeCloseConfirmation() {
        if(IntakeStates.getAutoCloseStates() != AutoCloseStates.waitForCommand) return false;

        ControlStates.setLeftBumperState(LeftBumperStates.autoIntakeCloseConfirmation);
        completeAction();
        return true;
    }

    private boolean moveSlidesUp() {
        if(intakeActive()) return false;

        ControlStates.setLeftBumperState(LeftBumperStates.moveSlidesUp);
        completeAction();
        return true;
    }

    private boolean subsystemsIdle() {
        return IntakeStates.getIntakeState() == SubsystemState.Idle && OuttakeStates.getOuttakeState() == SubsystemState.Idle;
    }
}
