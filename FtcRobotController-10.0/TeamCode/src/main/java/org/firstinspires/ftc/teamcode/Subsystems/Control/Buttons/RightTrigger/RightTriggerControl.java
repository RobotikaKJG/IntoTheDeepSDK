package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class RightTriggerControl {

    public RightTriggerControl() {
    }



    public void update() {
        switch(ButtonStates.getRightTriggerState()){
            case moveExtendoBack:
                moveExtendoBack();
                break;
            case idle:
                break;
        }
    }

    private void moveExtendoBack() {
        IntakeStates.setExtendoState(ExtendoStates.stepDown);
    }
}
