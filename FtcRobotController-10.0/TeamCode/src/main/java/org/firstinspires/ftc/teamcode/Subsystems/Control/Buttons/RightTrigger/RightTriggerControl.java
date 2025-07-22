package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.CloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class RightTriggerControl {

    public void update() {
        switch(ButtonStates.getRightTriggerState()){
            case closeIntake:
                closeIntake();
                break;
            case idle:
                break;
        }
    }

    private void closeIntake() {
        IntakeStates.setCloseStates(CloseStates.pivot);
    }
}
