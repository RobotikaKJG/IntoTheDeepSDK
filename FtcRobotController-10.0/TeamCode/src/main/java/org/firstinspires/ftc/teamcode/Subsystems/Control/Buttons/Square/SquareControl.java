package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Pivot.PivotStates;

public class SquareControl {
    public void update() {
        switch (ButtonStates.getSquareState()) {
            case up:
                OuttakeStates.setPivotStates(PivotStates.upFast);
                break;
            case down:
                OuttakeStates.setPivotStates(PivotStates.down);
                break;
            case idle:
                break;
        }
    }
}
