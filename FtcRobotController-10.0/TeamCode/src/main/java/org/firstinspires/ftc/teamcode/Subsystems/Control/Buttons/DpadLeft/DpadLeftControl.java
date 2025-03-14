package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadLeft;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;

public class DpadLeftControl {
    private boolean previousDpadLeftState = false;

    public void update(boolean dpadLeftPressed) {
        if (dpadLeftPressed && !previousDpadLeftState) {
            toggleSampleLock();
        }
        previousDpadLeftState = dpadLeftPressed;
    }

    private void toggleSampleLock() {
        if (OuttakeStates.getSampleLockState() == SampleLockStates.closed) {
            OuttakeStates.setSampleLockState(SampleLockStates.open);
        } else {
            OuttakeStates.setSampleLockState(SampleLockStates.closed);
        }
    }
}
