package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightTrigger;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

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
        IntakeStates.setVerticalSlideState(ArmSlideStates.stepDown);
        return;
    }
}
