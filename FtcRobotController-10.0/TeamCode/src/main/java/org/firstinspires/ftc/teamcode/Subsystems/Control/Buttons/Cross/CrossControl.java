package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Cross;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class CrossControl {
    public void update() {
        switch (ButtonStates.getCrossState()) {
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
