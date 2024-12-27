package org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class ReleaseButtonLogic {
    private double currentWait = 0;
    private final ElapsedTime elapsedTime;

    public ReleaseButtonLogic(ElapsedTime elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

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
            case openClaw:
                openClaw();
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
        OuttakeStates.setReleaseButtonState(ReleaseButtonStates.waitToFlip);
    }

    private void waitToFlip() {
        if(currentWait > elapsedTime.seconds()) return;
        OuttakeStates.setReleaseButtonState(ReleaseButtonStates.waitForReleaseConfirmation);
    }

    private void waitForReleaseConfirmation() {
        //nothing should happen here, it is used to check whether release can happen
    }

    private void releaseSample() {
        addWaitTime(OuttakeConstants.releaseServoWait);
        OuttakeStates.setReleaseButtonState(ReleaseButtonStates.waitToRelease);
    }

    private void waitToRelease(){
        if(currentWait > elapsedTime.seconds()) return;
        addWaitTime(OuttakeConstants.outtakeArmCloseWait);
        OuttakeStates.setReleaseButtonState(ReleaseButtonStates.retractArm);
    }

    private void retractArm() {
        if(currentWait > elapsedTime.seconds()) return;
        OuttakeStates.setReleaseButtonState(ReleaseButtonStates.openClaw);
    }

    private void openClaw() {
    }

    private void retractSlides() {
        OuttakeStates.setReleaseButtonState(ReleaseButtonStates.idle);
    }


    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }

}
