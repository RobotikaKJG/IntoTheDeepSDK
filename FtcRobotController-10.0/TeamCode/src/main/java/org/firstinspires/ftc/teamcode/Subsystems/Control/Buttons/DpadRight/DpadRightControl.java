package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class DpadRightControl {
    public void update() {
        switch (ButtonStates.getDpadRightState()) {
            case take:
                OuttakeStates.setArmState(ArmStates.takeSpecimen);
                break;
            case place:
                OuttakeStates.setArmState(ArmStates.placeSpecimen);
                break;
            case idle:
                break;
        }
    }
}
