package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Circle;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class CircleControl {
    public void update() {
        switch (ButtonStates.getCircleState()) {
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
