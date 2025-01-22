package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.ReleaseButtonStates;

public class SquareControl {
    public void update() {
        switch (ControlStates.getSquareState()) {
            case iterateOuttakeArmStates:
                iterateOuttakeArmStates();
                break;
            case manualToggleSampleClaw:
                manualToggleSampleClaw();
                break;
            case idle:
                break;
        }
    }

    private void iterateOuttakeArmStates() {
        switch (OuttakeStates.getReleaseButtonState()){
            case idle:
                OuttakeStates.setReleaseButtonState(ReleaseButtonStates.flipArm);
                break;
            case waitForReleaseConfirmation:
                OuttakeStates.setReleaseButtonState(ReleaseButtonStates.releaseSample);
                break;
            case releaseSample:
                OuttakeStates.setReleaseButtonState(ReleaseButtonStates.waitToRelease);
                break;
        }
    }

    private void manualToggleSampleClaw() {
        if(OuttakeStates.getSampleClawState() != SampleClawStates.closed)
            OuttakeStates.setSampleClawState(SampleClawStates.closed);
        else
            OuttakeStates.setSampleClawState(SampleClawStates.fullyOpen);
    }
}
