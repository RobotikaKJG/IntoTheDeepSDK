package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadDown;


import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class DpadDownLogic {
    private final DpadDownControl dpadDownControl = new DpadDownControl();

    public void update() {
        if(toggleEjectionServo()) return;
    }

    private void completeAction(){
        dpadDownControl.update();
        ControlStates.setDpadDownState(DpadDownStates.idle);
    }

    private boolean toggleEjectionServo() {
        if(outtakeActive() || extendoRetracted()) return false;
        ControlStates.setDpadDownState(DpadDownStates.toggleEjectionServo);
        completeAction();
        return true;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }

    private boolean extendoRetracted() {
        return IntakeStates.getExtendoState() == ExtendoStates.retracted;
    }

}
