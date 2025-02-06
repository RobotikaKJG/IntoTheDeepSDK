package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Cross;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class CrossControl {
    public void update() {
        switch (ControlStates.getCrossState()) {
            case takeSpecimen:
                takeSpecimen();
                break;
            case idle:
                break;
        }
    }

    private void takeSpecimen() {
        OuttakeStates.setSpecimenClawState(SpecimenClawStates.closed);
    }
}
