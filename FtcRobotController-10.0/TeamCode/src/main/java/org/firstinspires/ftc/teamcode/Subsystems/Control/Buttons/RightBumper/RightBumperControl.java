package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

public class RightBumperControl {
    public void update() {
        switch (ButtonStates.getRightBumperState()) {
            case stepUp:
                stepUp();
                break;
            case idle:
                break;
        }
    }
    private void stepUp() {
        IntakeStates.setVerticalSlideState(ArmSlideStates.stepUp);
        return;
    }
}
