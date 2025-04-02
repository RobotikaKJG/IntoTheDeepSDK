package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Circle;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.DropSampleActions.DropSampleStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class CircleControl {
    public void update() {
        switch (ButtonStates.getCircleState()) {
            case dropSample:
                dropSample();
                break;
            case idle:
                break;
        }
    }

    private void dropSample() {
        OuttakeStates.setDropSampleState(DropSampleStates.raiseSlides);
    }
}
