package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.HangStates;

public class DpadRightControl {
    public void update() {
        switch (DpadRightStates.getDpadRightState()) {
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
        if (HangStates.getHangState() == HangStates.RETRACTED) {
            // If retracted, command extension.
            HangStates.setHangState(HangStates.EXTENDING);
        } else if (HangStates.getHangState() == HangStates.EXTENDED) {
            // If extended, command retraction.
            HangStates.setHangState(HangStates.RETRACTING);
        }
        // If the slides are already moving (EXTENDING or RETRACTING), ignore new commands.
    }
}
