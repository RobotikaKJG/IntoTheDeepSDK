package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class SquareLogic {
    private final SquareControl squareControl = new SquareControl();

    public void update() {
        if (closeClaw()) return;
        if (openClaw()) return;
    }

    private void completeAction(){
        squareControl.update();
        ButtonStates.setSquareState(SquareStates.idle);
    }

    private boolean closeClaw() {
        if (OuttakeStates.getSpecimenClawState() == SpecimenClawStates.closed) return false;
        ButtonStates.setSquareState(SquareStates.closeClaw);
        completeAction();
        return true;
    }

    private boolean openClaw() {
        if (OuttakeStates.getSpecimenClawState() == SpecimenClawStates.fullyOpen) return false;
        ButtonStates.setSquareState(SquareStates.openClaw);
        completeAction();
        return true;
    }
}
