package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class DpadRightLogic {
    private final DpadRightControl dpadRightControl = new DpadRightControl();

    public void update() {
        takeSpecimen();
    }

    private void completeAction(){
        dpadRightControl.update();
        ButtonStates.setDpadRightState(DpadRightStates.idle);
    }

    private void takeSpecimen() {
        ButtonStates.setDpadRightState(DpadRightStates.takeSpecimen);
        completeAction();
    }
}
