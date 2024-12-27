package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class RightTriggerLogic {
    private final RightTriggerControl rightTriggerControl = new RightTriggerControl();

    public RightTriggerLogic() {
    }

    public void update() {
        if(retractExtendo()) return;
    }

    private void completeAction(){
        rightTriggerControl.update();
        ControlStates.setRightTriggerState(RightTriggerStates.idle);
    }

    private boolean retractExtendo() {
        if(intakeInactive()) return false;

        ControlStates.setRightTriggerState(RightTriggerStates.moveExtendoBack);
        completeAction();
        return true;
    }

    private boolean intakeInactive() {
        return IntakeStates.getIntakeState() == SubsystemState.Idle;
    }
}
