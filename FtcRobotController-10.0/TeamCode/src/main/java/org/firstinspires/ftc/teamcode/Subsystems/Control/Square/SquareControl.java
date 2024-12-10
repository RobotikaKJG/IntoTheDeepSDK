package org.firstinspires.ftc.teamcode.Subsystems.Control.Square;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.ReleaseButtonStates;

public class SquareControl {
    public void update() {
        switch (ControlStates.getSquareState()) {
            case iterateOuttakeArmStates:
                iterateOuttakeArmStates();
                break;
            case manualToggleClaw:
                manualToggleClaw();
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
            case openClaw:
                OuttakeStates.setReleaseButtonState(ReleaseButtonStates.retractSlides);
                break;
        }
    }

    private void manualToggleClaw() {
        if(OuttakeStates.getClawState() != ClawStates.closed)
            OuttakeStates.setClawState(ClawStates.closed);
        else
            OuttakeStates.setClawState(ClawStates.fullyOpen);
    }
}
