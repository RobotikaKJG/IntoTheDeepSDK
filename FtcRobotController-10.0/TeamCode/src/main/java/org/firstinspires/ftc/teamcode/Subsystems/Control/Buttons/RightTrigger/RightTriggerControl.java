package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class RightTriggerControl {
    public void update() {
        switch (ButtonStates.getRightTriggerState()) {
            case stepDown:
                stepDown();
                break;
            case idle:
                break;
        }
    }
    private void stepDown() {
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.stepDown);
        return;
    }
}
