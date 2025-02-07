package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square;


import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class SquareLogic {
    private final SquareControl squareControl = new SquareControl();

    public void update() {
        if(iterateSampleReleaseStates()) return;
        if(manualToggleSampleClaw()) return;
        if(placeSpecimen()) return;
    }

    private void completeAction(){
        squareControl.update();
        ButtonStates.setSquareState(SquareStates.idle);
    }

    private boolean iterateSampleReleaseStates() {
        if(!outtakeActive() || specimenTaken()) return false;
        ButtonStates.setSquareState(SquareStates.iterateSampleReleaseStates);
        completeAction();
        return true;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }

    private boolean manualToggleSampleClaw()
    {
        if(outtakeActive()) return false;
        ButtonStates.setSquareState(SquareStates.manualToggleSampleClaw);
        completeAction();
        return true;
    }

    private boolean intakeActive() {
        return IntakeStates.getIntakeState() == SubsystemState.Run;
    }

    private boolean specimenTaken(){
        return OuttakeStates.getSpecimenClawState() == SpecimenClawStates.closed;
    }

    private boolean placeSpecimen() {
        if(!outtakeActive() || intakeActive() || !specimenTaken()) return false;
        ButtonStates.setSquareState(SquareStates.placeSpecimen);
        completeAction();
        return true;
    }
}
