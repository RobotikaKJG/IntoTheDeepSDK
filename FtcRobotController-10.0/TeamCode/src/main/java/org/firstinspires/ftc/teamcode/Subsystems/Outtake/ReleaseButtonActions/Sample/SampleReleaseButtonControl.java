package org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class SampleReleaseButtonControl {
    private SampleReleaseButtonStates prevSampleReleaseButtonStates = SampleReleaseButtonStates.idle;

    public void update(){
        if(OuttakeStates.getReleaseButtonState() != prevSampleReleaseButtonStates) {
            updateStates();
            prevSampleReleaseButtonStates = OuttakeStates.getReleaseButtonState();
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
