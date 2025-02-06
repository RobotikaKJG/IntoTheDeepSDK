package org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class SpecimenReleaseButtonLogic {
    private double currentWait = 0;
    private final SlideLogic slideLogic;

    public SpecimenReleaseButtonLogic(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void update() {
        switch (OuttakeStates.getSpecimenReleaseButtonState()) {
            case clipOn:
                clipOn();
                break;
            case waitToRelease:
                waitToRelease();
                break;
            case release:
                release();
                break;
            case close:
                close();
                break;
            case idle:
                idle();
                break;
        }
    }

    private void clipOn() {
        OuttakeStates.setSpecimenReleaseButtonState(SpecimenReleaseButtonStates.waitToRelease);
    }

    private void waitToRelease() {
        if(Math.abs(slideLogic.getSlidePosition() - slideLogic.getSlideExtensionTarget()) > OuttakeConstants.specimenHungThreshold) return;
        OuttakeStates.setSpecimenReleaseButtonState(SpecimenReleaseButtonStates.release);
        addWaitTime(OuttakeConstants.specimenReleaseWait);
    }

    private void release() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setSpecimenReleaseButtonState(SpecimenReleaseButtonStates.close);

    }

    private void close() {
        if(OuttakeStates.getVerticalSlideState() != VerticalSlideStates.closed) return;
        OuttakeStates.setSpecimenReleaseButtonState(SpecimenReleaseButtonStates.idle);
    }

    private void idle() {

    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}
