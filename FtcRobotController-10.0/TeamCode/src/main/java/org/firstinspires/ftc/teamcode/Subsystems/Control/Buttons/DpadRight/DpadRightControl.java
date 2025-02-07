package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
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
        if (OuttakeStates.getHangState() == HangStates.retracted) {
            // If retracted, command extension.
            OuttakeStates.setHangState(HangStates.extending);
        } else if (OuttakeStates.getHangState() == HangStates.extended) {
            // If extended, command retraction.
            OuttakeStates.setHangState(HangStates.retracting);
        }
        // If the slides are already moving (EXTENDING or RETRACTING), ignore new commands.
    }
}
