package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;

import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class LeftBumperLogic {
    private final LeftBumperControl leftBumperControl = new LeftBumperControl();
    
    public void update()
    {
        if(closeIntake()) return;
        if(moveSlidesUp()) return;
    }

    private void completeAction(){
        leftBumperControl.update();
        ButtonStates.setLeftBumperState(LeftBumperStates.idle);
    }

    private boolean closeIntake() {
        if(!intakeActive()) return false;

        ButtonStates.setLeftBumperState(LeftBumperStates.closeIntake);
        completeAction();
        return true;
    }

    private boolean moveSlidesUp() {
        if(intakeActive()) return false;

        ButtonStates.setLeftBumperState(LeftBumperStates.moveSlidesUp);
        completeAction();
        return true;
    }

    private boolean intakeActive(){
        return IntakeStates.getIntakeState() != SubsystemState.Idle;
    }
}
