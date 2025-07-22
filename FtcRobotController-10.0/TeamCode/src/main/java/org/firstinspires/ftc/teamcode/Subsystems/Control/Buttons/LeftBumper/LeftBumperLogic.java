package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.CloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class LeftBumperLogic {
    private final LeftBumperControl leftBumperControl = new LeftBumperControl();
    
    public void update()
    {
        if(toggleIntakeMotorBackward()) return;
        if(moveSlidesUp()) return;
    }

    private void completeAction(){
        leftBumperControl.update();
        ButtonStates.setLeftBumperState(LeftBumperStates.idle);
    }

    private boolean toggleIntakeMotorBackward() {
        if(intakeClosing()) return false;

        ButtonStates.setLeftBumperState(LeftBumperStates.toggleIntakeMotorBackward);
        completeAction();
        return true;
    }

    private boolean intakeActive(){
        return IntakeStates.getIntakeState() != SubsystemState.Idle;
    }


    private boolean moveSlidesUp() {
        if(intakeActive()) return false;

        ButtonStates.setLeftBumperState(LeftBumperStates.moveSlidesUp);
        completeAction();
        return true;
    }

    private boolean intakeClosing(){
        return IntakeStates.getCloseStates() == CloseStates.waitForCommand ||
                IntakeStates.getCloseStates() == CloseStates.pivot ||
                IntakeStates.getCloseStates() == CloseStates.waitToPivot ||
                IntakeStates.getCloseStates() == CloseStates.waitToRetract ||
                IntakeStates.getCloseStates() == CloseStates.closeSampleClaw ||
                IntakeStates.getCloseStates() == CloseStates.idle;
    }

    private boolean outtakeActive(){
        return OuttakeStates.getOuttakeState() != SubsystemState.Idle;
    }
}
