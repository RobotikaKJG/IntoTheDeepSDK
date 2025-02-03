package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Circle;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen.SpecimenReleaseButtonStates;

public class CircleControl {
    public void update() {
        switch (ControlStates.getCircleState()) {
            case dropSample:
                dropSample();
                break;
            case idle:
                break;
        }
    }

    private void dropSample() {
        OuttakeStates.setSpecimenReleaseButtonState(SpecimenReleaseButtonStates.clipOn);
    }
}
