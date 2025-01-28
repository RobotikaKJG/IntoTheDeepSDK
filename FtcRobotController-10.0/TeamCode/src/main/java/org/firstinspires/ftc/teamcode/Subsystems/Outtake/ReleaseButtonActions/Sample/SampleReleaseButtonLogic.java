package org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.Sample;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class SampleReleaseButtonLogic {
    private double currentWait = 0;

    public void update(){
        switch(OuttakeStates.getReleaseButtonState()){
            case idle:
                idle();
                break;
            case flipArm:
                flipArm();
                break;
            case waitToFlip:
                waitToFlip();
                break;
            case waitForReleaseConfirmation:
                waitForReleaseConfirmation();
                break;
            case releaseSample:
                releaseSample();
                break;
            case waitToRelease:
                waitToRelease();
                break;
            case retractArm:
                retractArm();
                break;
            case openSampleClaw:
                openSampleClaw();
                break;
            case retractSlides:
                retractSlides();
                break;
        }
    }

    private void idle(){

    }

    private void flipArm() {
        addWaitTime(OuttakeConstants.outtakeArmWait);
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.waitToFlip);
    }

    private void waitToFlip() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.waitForReleaseConfirmation);
    }

    private void waitForReleaseConfirmation() {
        //nothing should happen here, it is used to check whether release can happen
    }

    private void releaseSample() {
        addWaitTime(OuttakeConstants.releaseServoWait);
    }

    private void waitToRelease(){
        if(currentWait > getSeconds()) return;
        if(OuttakeStates.getVerticalSlideState() == VerticalSlideStates.lowBasket)
            addWaitTime(OuttakeConstants.outtakeArmCloseWait);
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.retractArm);
    }

    private void retractArm() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.openSampleClaw);
    }

    private void openSampleClaw() {
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.retractSlides);
    }

    private void retractSlides() {
        OuttakeStates.setReleaseButtonState(SampleReleaseButtonStates.waitToRetract);
    }


    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}
