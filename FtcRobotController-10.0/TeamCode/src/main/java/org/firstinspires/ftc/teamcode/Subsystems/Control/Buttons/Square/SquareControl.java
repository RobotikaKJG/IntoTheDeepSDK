package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class SquareControl {
    public void update() {
        switch (ButtonStates.getSquareState()) {
            case up:
                OuttakeStates.setPivotStates(PivotStates.up);
                break;
            case down:
                OuttakeStates.setPivotStates(PivotStates.down);
                break;
            case idle:
                break;
        }
    }
}
