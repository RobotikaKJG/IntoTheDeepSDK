package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper;

import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class RightBumperLogic {
    private final RightBumperControl rightBumperControl = new RightBumperControl();

    public RightBumperLogic() {
    }

    public void update() {
        if(extendExtendo()) return;
    }

    private void completeAction(){
        rightBumperControl.update();
        ControlStates.setRightBumperState(RightBumperStates.idle);
    }

    private boolean extendExtendo() {
        if(outtakeActive()) return false;

        ControlStates.setRightBumperState(RightBumperStates.moveExtendoForward);
        completeAction();
        return true;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() != SubsystemState.Idle;
    }

    private boolean extendoExtended() {
        return IntakeStates.getExtendoState() == ExtendoStates.extended;
    }
}
