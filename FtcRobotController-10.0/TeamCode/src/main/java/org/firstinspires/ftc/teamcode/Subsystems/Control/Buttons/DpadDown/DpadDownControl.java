package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadDown;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class DpadDownControl {
    public void update() {
        switch (ButtonStates.getDpadDownState()) {
            case down:
                OuttakeStates.setArmState(ArmStates.down);
                break;
            case idle:
                break;
        }
    }
}
