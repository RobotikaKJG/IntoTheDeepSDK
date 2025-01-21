package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;

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
        if(outtakeActive()) return false;

        ControlStates.setRightTriggerState(RightTriggerStates.moveExtendoBack);
        completeAction();
        return true;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }
}
