package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;

public class RightBumperControl {

    public RightBumperControl() {
    }

    public void update() {
        switch(ControlStates.getRightBumperState()){
            case moveExtendoForward:
                moveExtendoForward();
                break;
            case idle:
                break;
        }
    }

    private void moveExtendoForward() {
        IntakeStates.setExtendoState(ExtendoStates.extended);
    }
}
