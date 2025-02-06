package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class HangControl {
    private final SlideLogic slideLogic;
    private HangStates prevHangState = OuttakeStates.getHangState();

    public HangControl(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void update() {
        // If the hang state has changed, update the slide target accordingly.
        if (OuttakeStates.getHangState() != prevHangState) {
            updateStates();
            prevHangState = OuttakeStates.getHangState();
        }

        // Check if the slide has reached its target position for extending.
        if (OuttakeStates.getHangState() == HangStates.EXTENDING) {
            if (isAtTarget()) {
                // When close enough to the target (e.g. within the threshold),
                // update the state to EXTENDED.
                OuttakeStates.setHangState(HangStates.EXTENDED);
            }
        }

        // Check if the slide has reached its target position for retracting.
        if (OuttakeStates.getHangState() == HangStates.RETRACTING) {

            if (isAtTarget()) {
                OuttakeStates.setHangState(HangStates.RETRACTED);
            }
        }
    }


    private boolean isAtTarget() {
        return Math.abs(slideLogic.getSlidePosition() - slideLogic.getSlideExtensionTarget()) < OuttakeConstants.hangThreshold;
    }

    private void updateStates() {
        switch (OuttakeStates.getHangState()) {
            case EXTENDING:
                // Command the slides to extend to high basket.
                slideLogic.setSlideExtensionTarget(OuttakeConstants.hangBar);
                break;
            case RETRACTING:
                // Command the slides to retract (target position set to 20).
                slideLogic.setSlideExtensionTarget(20);
                break;
            default:
                // For EXTENDED and RETRACTED, no change is needed.
                break;
        }
    }
}
