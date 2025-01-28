package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.Square;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample.SampleReleaseButtonStates;

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
}
