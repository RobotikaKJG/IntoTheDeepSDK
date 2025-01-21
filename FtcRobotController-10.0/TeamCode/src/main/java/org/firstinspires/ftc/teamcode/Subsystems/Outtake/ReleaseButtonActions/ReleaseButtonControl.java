package org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions;

import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
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
            case openClaw:
                openClaw();
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
    }

    private void openClaw() {
        OuttakeStates.setClawState(ClawStates.fullyOpen);
    }

    private void retractSlides() {
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
    }


}
