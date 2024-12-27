package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadUp;


import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class DpadUpLogic {
    private final DpadUpControl dpadUpControl = new DpadUpControl();

    public void update() {
        if(reverseMotor()) return;
    }

    private void completeAction(){
        dpadUpControl.update();
        ControlStates.setDpadUpState(DpadUpStates.idle);
    }

    private boolean reverseMotor() {
        if(outtakeActive()) return false;
        ControlStates.setDpadUpState(DpadUpStates.toggleMotor);
        completeAction();
        return true;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }
}
