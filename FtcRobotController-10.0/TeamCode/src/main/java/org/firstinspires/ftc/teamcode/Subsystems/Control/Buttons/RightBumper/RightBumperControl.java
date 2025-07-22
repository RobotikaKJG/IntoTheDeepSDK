package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class RightBumperControl {

    public RightBumperControl() {
    }

    public void update() {
        switch(ButtonStates.getRightBumperState()){
            case moveExtendoForward:
                moveExtendoForward();
                break;
            case idle:
                break;
        }
    }

    private void moveExtendoForward() {
        IntakeStates.setExtendoState(ExtendoStates.stepUp);
    }
}
