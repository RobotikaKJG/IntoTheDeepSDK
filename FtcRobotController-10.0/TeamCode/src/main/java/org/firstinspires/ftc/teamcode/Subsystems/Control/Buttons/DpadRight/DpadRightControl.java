package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang.HangStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;


public class DpadRightControl {
    public void update() {
        switch (ControlStates.getDpadRightState()) {
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
        if (OuttakeStates.getHangState() == HangStates.RETRACTED) {
            // If retracted, command extension.
            OuttakeStates.setHangState(HangStates.EXTENDING);
        } else if (OuttakeStates.getHangState() == HangStates.EXTENDED) {
            // If extended, command retraction.
            OuttakeStates.setHangState(HangStates.RETRACTING);
        }
        // If the slides are already moving (EXTENDING or RETRACTING), ignore new commands.
    }
}
