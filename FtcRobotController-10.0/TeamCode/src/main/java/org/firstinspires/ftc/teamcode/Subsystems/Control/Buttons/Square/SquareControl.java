package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen.SpecimenReleaseButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;

public class SquareControl {
    public void update() {
        switch (ButtonStates.getSquareState()) {
            case iterateSampleReleaseStates:
                iterateSampleReleaseStates();
                break;
            case manualToggleSampleClaw:
                manualToggleSampleClaw();
                break;
            case placeSpecimen:
                placeSpecimen();
                break;
            case idle:
                break;
        }
    }

    private void iterateSampleReleaseStates() {
        switch (OuttakeStates.getSampleReleaseButtonState()){
            case idle:
                OuttakeStates.setSampleReleaseButtonState(SampleReleaseButtonStates.flipArm);
                break;
            case waitForReleaseConfirmation:
                OuttakeStates.setSampleReleaseButtonState(SampleReleaseButtonStates.releaseSample);
                break;
            case releaseSample:
                OuttakeStates.setSampleReleaseButtonState(SampleReleaseButtonStates.waitToRelease);
                break;
        }
    }

    private void manualToggleSampleClaw() {
        if(OuttakeStates.getSampleClawState() != SampleClawStates.closed)
            OuttakeStates.setSampleClawState(SampleClawStates.closed);
        else
            OuttakeStates.setSampleClawState(SampleClawStates.fullyOpen);
    }

    private void placeSpecimen() {
        OuttakeStates.setSpecimenReleaseButtonState(SpecimenReleaseButtonStates.clipOn);
    }
}
