package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper;

import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper.RightBumperStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.CloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
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
        if(releaseClaw()) return;
    }

    private void completeAction(){
        leftBumperControl.update();
        ButtonStates.setLeftBumperState(LeftBumperStates.idle);
    }

    private boolean toggleIntakeMotorBackward() {
        if(!intakeActive()) return false;

        ButtonStates.setLeftBumperState(LeftBumperStates.toggleIntakeMotorBackward);
        completeAction();
        return true;
    }

    private boolean ejectionReady() {
        return IntakeStates.getExtendoState() == ExtendoStates.extended && IntakeStates.getPivotState() == PivotStates.overSub;
    }

    private boolean moveSlidesUp() {
        if(intakeActive() || outtakeActive()) return false;

        ButtonStates.setLeftBumperState(LeftBumperStates.moveSlidesUp);
        completeAction();
        return true;
    }

    private boolean releaseClaw() {
        if (!outtakeActive()) return false;

        ButtonStates.setLeftBumperState(LeftBumperStates.openClaw);
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

    private boolean intakeActive(){
        return IntakeStates.getIntakeState() != SubsystemState.Idle;
    }
}
