package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadRight;

public class DpadRightLogic {
    private final DpadRightControl dpadRightControl = new DpadRightControl();
    private boolean previousButtonState = false;

    /**
     * Call this update method periodically (passing in the current gamepad dpad_right value).
     * @param currentButtonState true if the dpad_right button is currently pressed.
     */
    public void update(boolean currentButtonState) {
        // Detect a rising edge: button was not pressed previously, but is pressed now.
        if (currentButtonState && !previousButtonState) {
            DpadRightStates.setDpadRightState(DpadRightStates.toggleHang);
            dpadRightControl.update();
            // Reset the dpad-right command state to idle.
            DpadRightStates.setDpadRightState(DpadRightStates.idle);
        }
        previousButtonState = currentButtonState;
    }
}
