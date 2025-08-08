package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class SquareControl {
    public void update() {
        switch (ButtonStates.getSquareState()) {
            case closeClaw:
                OuttakeStates.setSpecimenClawState(SpecimenClawStates.closed);
                break;
            case openClaw:
                OuttakeStates.setSpecimenClawState(SpecimenClawStates.freeMove);
                break;
            case idle:
                break;
        }
    }
}
