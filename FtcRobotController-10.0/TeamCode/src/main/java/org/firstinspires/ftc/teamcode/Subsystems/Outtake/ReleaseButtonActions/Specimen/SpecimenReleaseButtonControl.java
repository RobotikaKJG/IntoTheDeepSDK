package org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Specimen;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw.SpecimenClawStates;

public class SpecimenReleaseButtonControl {
    private SpecimenReleaseButtonStates prevSpecimenReleaseButtonStates = SpecimenReleaseButtonStates.idle;

    public SpecimenReleaseButtonControl() {

    }

    public void update(){
        if(OuttakeStates.getSpecimenReleaseButtonState() != prevSpecimenReleaseButtonStates) {
            updateStates();
            prevSpecimenReleaseButtonStates = OuttakeStates.getSpecimenReleaseButtonState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getSpecimenReleaseButtonState()) {
            case clipOn:
                clipOn();
                break;
            case release:
                release();
                break;
            case close:
                close();
                break;
        }
    }

    private void clipOn() {
        switch (OuttakeStates.getVerticalSlideState()) {
            case lowRung:
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.lowRungScore);
                break;
            case highRung:
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.highRungScore);
                break;
        }
    }

    private void release() {
        OuttakeStates.setSpecimenClawState(SpecimenClawStates.open);
    }

    private void close() {
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
    }
}
