package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square;


import org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Circle.CircleStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
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
        ControlStates.setSquareState(SquareStates.idle);
    }

    private boolean iterateSampleReleaseStates() {
        if(!outtakeActive()) return false;
        ControlStates.setSquareState(SquareStates.iterateSampleReleaseStates);
        completeAction();
        return true;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }

    private boolean manualToggleSampleClaw()
    {
        if(outtakeActive()) return false;
        ControlStates.setSquareState(SquareStates.manualToggleSampleClaw);
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
        ControlStates.setCircleState(CircleStates.dropSample);
        completeAction();
        return true;
    }
}
