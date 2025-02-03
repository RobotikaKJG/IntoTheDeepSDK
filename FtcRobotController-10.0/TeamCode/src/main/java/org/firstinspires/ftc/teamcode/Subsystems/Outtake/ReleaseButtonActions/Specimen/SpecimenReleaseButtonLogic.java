package org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class SpecimenReleaseButtonLogic {
    private double currentWait = 0;

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
        addWaitTime(OuttakeConstants.specimenClipOnWait);
    }

    private void waitToRelease() {
        if(currentWait > getSeconds()) return;
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
