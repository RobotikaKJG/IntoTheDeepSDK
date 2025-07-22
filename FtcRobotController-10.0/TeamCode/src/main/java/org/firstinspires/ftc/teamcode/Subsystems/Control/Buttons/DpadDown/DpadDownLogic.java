package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadDown;


import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class DpadDownLogic {
    private final DpadDownControl dpadDownControl = new DpadDownControl();

    public void update() {
//        if(toggleEjectionServo()) return;
//        if(slideStepDown()) return;
        if(moveExtendoBack()) return;
    }

    private void completeAction(){
        dpadDownControl.update();
        ButtonStates.setDpadDownState(DpadDownStates.idle);
    }


//    private boolean slideStepDown() {
//        if(!sampleTaken()) return false;
//        ButtonStates.setDpadDownState(DpadDownStates.slideStepDown);
//        completeAction();
//        return true;
//    }

    private boolean moveExtendoBack() {
        if(outtakeActive()) return false;

        ButtonStates.setDpadDownState(DpadDownStates.moveExtendoBack);
        completeAction();
        return true;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }

    private boolean sampleTaken(){
        return OuttakeStates.getClawState() == ClawStates.closed || OuttakeStates.getClawState() == ClawStates.freeMove;
    }
}
