package org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class SampleReleaseButtonControl {
    private SampleReleaseButtonStates prevSampleReleaseButtonStates = SampleReleaseButtonStates.idle;

    public void update(){
        if(OuttakeStates.getSampleReleaseButtonState() != prevSampleReleaseButtonStates) {
            updateStates();
            prevSampleReleaseButtonStates = OuttakeStates.getSampleReleaseButtonState();
        }
    }

    private void updateStates(){
        switch(OuttakeStates.getSampleReleaseButtonState()){
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
        OuttakeStates.setClawState(ClawStates.closed);
    }

    private void releaseSample() {
        OuttakeStates.setClawState(ClawStates.halfOpen);
    }

    private void waitToRelease() {
        OuttakeStates.setArmState(ArmStates.down);
    } //Bad names, NOTE

    private void openSampleClaw() {
        OuttakeStates.setClawState(ClawStates.fullyOpen);
    }

    private void retractSlides() {
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
    }


}
