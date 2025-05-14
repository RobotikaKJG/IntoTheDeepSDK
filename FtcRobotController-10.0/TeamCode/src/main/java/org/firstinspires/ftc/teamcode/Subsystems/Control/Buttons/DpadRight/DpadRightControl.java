package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang.HangStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;


public class DpadRightControl {
    public void update() {
        switch (ButtonStates.getDpadRightState()) {
            case toggleHang:
                toggleHang();
                break;
            case idle:
                // Do nothing.
                break;
        }
    }

    private void toggleHang() {
        // Toggle only if the slides are fully retracted or fully extended.
        if (OuttakeStates.getArmState() == ArmStates.up)
            OuttakeStates.setArmState(ArmStates.down);
        else
            OuttakeStates.setHangState(HangStates.retracting);
//        }
        // If the slides are already moving (EXTENDING or RETRACTING), ignore new commands.
    }
}
