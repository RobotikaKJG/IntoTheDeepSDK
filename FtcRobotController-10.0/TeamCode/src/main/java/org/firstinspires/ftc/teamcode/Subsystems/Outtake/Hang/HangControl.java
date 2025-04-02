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
        if (OuttakeStates.getHangState() != prevHangState) {
            updateStates();
            prevHangState = OuttakeStates.getHangState();
        }

        if (OuttakeStates.getHangState() == HangStates.extending) {
            if (isAtTarget()) {
                OuttakeStates.setHangState(HangStates.extended);
            }
        }

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
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.hang);
                break;
            case retracting:
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
                IntakeStates.setExtendoState(ExtendoStates.hold);
                break;
        }
    }
}
