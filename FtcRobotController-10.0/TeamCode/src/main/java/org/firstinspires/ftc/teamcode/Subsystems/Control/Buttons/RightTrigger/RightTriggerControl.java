package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeRotationStates;

public class RightTriggerControl {

    public RightTriggerControl() {
    }



    public void update() {
        switch(ControlStates.getRightTriggerState()){
            case moveExtendoBack:
                moveExtendoBack();
                break;
            case idle:
                break;
        }
    }

    private void moveExtendoBack() {
        IntakeStates.setExtendoState(ExtendoStates.retracting);
    }
}
