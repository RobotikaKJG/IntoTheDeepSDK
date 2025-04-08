package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

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
        if (OuttakeStates.getHangState() == HangStates.extending) {
            if (isAtTarget()) {
                // When close enough to the target (e.g. within the threshold),
                // update the state to EXTENDED.
                OuttakeStates.setHangState(HangStates.extended);
            }
        }

        // Check if the slide has reached its target position for retracting.
        if (OuttakeStates.getHangState() == HangStates.retracting) {

            if (isAtTarget()) {
                OuttakeStates.setHangState(HangStates.retracted);
            }
        }
    }


    private boolean isAtTarget() {
        return Math.abs(slideLogic.getSlidePosition() - slideLogic.getSlideExtensionTarget()) < OuttakeConstants.hangThreshold;
    }

    private void updateStates() {
        switch (OuttakeStates.getHangState()) {
            case extending:
                // Command the slides to extend to high basket.

                break;
            case retracting:
                // Command the slides to retract (target position set to 20).
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.hang);
                //IntakeStates.setExtendoState(ExtendoStates.hold);
                break;
            default:
                // For EXTENDED and RETRACTED, no change is needed.
                break;
        }
    }
}
