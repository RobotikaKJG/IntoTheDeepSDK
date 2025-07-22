package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger;

import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftBumper.LeftBumperStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.CloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;

public class RightTriggerLogic {
    private final RightTriggerControl rightTriggerControl = new RightTriggerControl();

    public void update() {
        if(closeIntake()) return;
    }

    private void completeAction(){
        rightTriggerControl.update();
        ButtonStates.setRightTriggerState(RightTriggerStates.idle);
    }


    private boolean closeIntake() {
        if(IntakeStates.getCloseStates() != CloseStates.waitForCommand ||
                IntakeStates.getCloseStates() != CloseStates.checkColor) return false; //temporary?, NIGHTNOTE

        ButtonStates.setRightTriggerState(RightTriggerStates.closeIntake);
        completeAction();
        return true;
    }
}
