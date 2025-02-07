package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Cross;


import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;

public class CrossLogic {
    private final CrossControl crossControl = new CrossControl();

    public void update() {
        if(takeSpecimen()) return;
    }

    private void completeAction(){
        crossControl.update();
        ButtonStates.setCrossState(CrossStates.idle);
    }

    private boolean takeSpecimen() {
        if(outtakeActive() || intakeActive() || specimenTaken()) return false;
        ButtonStates.setCrossState(CrossStates.takeSpecimen);
        completeAction();
        return true;
    }

    private boolean outtakeActive() {
        return OuttakeStates.getOuttakeState() == SubsystemState.Run;
    }

    private boolean intakeActive() {
        return IntakeStates.getIntakeState() == SubsystemState.Run;
    }

    private boolean specimenTaken(){
        return OuttakeStates.getSpecimenClawState() == SpecimenClawStates.closed;
    }
}
