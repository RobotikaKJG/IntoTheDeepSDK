package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;

public class SquareLogic {
    private final SquareControl squareControl = new SquareControl();

    public void update() {
        if (up()) return;
        if (down()) return;
    }

    private void completeAction(){
        squareControl.update();
        ButtonStates.setSquareState(SquareStates.idle);
    }

    private boolean up() {
        if (IntakeStates.getPivotState() == PivotStates.upFast || IntakeStates.getPivotState() == PivotStates.up) return false;
        ButtonStates.setSquareState(SquareStates.up);
        completeAction();
        return true;
    }

    private boolean down() {
        if (IntakeStates.getPivotState() == PivotStates.down) return false;
        ButtonStates.setSquareState(SquareStates.down);
        completeAction();
        return true;
    }
}
