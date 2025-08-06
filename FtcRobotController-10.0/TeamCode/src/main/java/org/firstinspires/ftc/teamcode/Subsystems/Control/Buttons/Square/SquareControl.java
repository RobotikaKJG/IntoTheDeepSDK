package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;

public class SquareControl {
    public void update() {
        switch (ButtonStates.getSquareState()) {
            case up:
                IntakeStates.setPivotStates(PivotStates.upFast);
                break;
            case down:
                IntakeStates.setPivotStates(PivotStates.down);
                break;
            case idle:
                break;
        }
    }
}
