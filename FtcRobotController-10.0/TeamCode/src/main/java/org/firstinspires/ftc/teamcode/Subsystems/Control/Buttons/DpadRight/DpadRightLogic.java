package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class DpadRightLogic {
    private final DpadRightControl dpadRightControl = new DpadRightControl();

    public void update() {
        if (takeSpecimen()) return;
        if (placeSpecimen()) return;
    }

    private void completeAction(){
        dpadRightControl.update();
        ButtonStates.setDpadRightState(DpadRightStates.idle);
    }

    private boolean takeSpecimen() {
        if(OuttakeStates.getArmState() == ArmStates.takeSpecimen) return false;
        ButtonStates.setDpadRightState(DpadRightStates.take);
        completeAction();
        return true;
    }

    private boolean placeSpecimen() {
        if(OuttakeStates.getArmState() == ArmStates.placeSpecimen) return false;
        ButtonStates.setDpadRightState(DpadRightStates.place);
        completeAction();
        return true;
    }
}
