package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.RightBumper;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

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
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.stepUp);
        return;
    }
}
