package org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class ReleaseButtonControl {
    private ReleaseButtonStates prevReleaseButtonStates = ReleaseButtonStates.idle;

    public void update(){
        if(OuttakeStates.getReleaseButtonState() != prevReleaseButtonStates) {
            updateStates();
            prevReleaseButtonStates = OuttakeStates.getReleaseButtonState();
        }
    }

    private void updateStates(){
        switch(OuttakeStates.getReleaseButtonState()){
            case flipArm:
                flipArm();
                break;
            case releaseSample:
                releaseSample();
                break;
            case waitToRelease:
                waitToRelease();
                break;
            case openSampleClaw:
                openSampleClaw();
                break;
            case retractSlides:
                retractSlides();
                break;
            case idle:
                break;
        }
    }

    private void flipArm() {
        OuttakeStates.setArmState(ArmStates.up);
        OuttakeStates.setSampleClawState(SampleClawStates.closed);
    }

    private void releaseSample() {
        OuttakeStates.setSampleClawState(SampleClawStates.halfOpen);
    }

    private void waitToRelease() {
        OuttakeStates.setArmState(ArmStates.down);
    }

    private void openSampleClaw() {
        OuttakeStates.setSampleClawState(SampleClawStates.fullyOpen);
    }

    private void retractSlides() {
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
    }


}
