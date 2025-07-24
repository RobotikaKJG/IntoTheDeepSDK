package org.firstinspires.ftc.teamcode.Subsystems.Outtake.ActivateOuttakeActions;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class ActivateOuttakeLogic {
    private double currentWait = 0;
    private boolean wasIfCalled = false;
    private final SlideLogic slideLogic;

    public ActivateOuttakeLogic(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void update(){
        switch(OuttakeStates.getActivateOuttakeState()){
            case rotateArmUp:
                rotateArmUp();
                break;
            case rotateIntakePivot:
                rotateIntakePivot();
                break;
            case rotateArmDown:
                rotateOuttakePivot();
                break;
            case closeClaw:
                closeClaw();
                break;
            case raiseSlides:
                raiseSlides();
                break;
            case rotateArm:
                rotateArm();
                break;
            case idle:
                idle();
                break;

        }
    }

    private void rotateArmUp() {
        if(!wasIfCalled){
            addWaitTime(OuttakeConstants.rotateOuttakeArmWait);
            wasIfCalled = true;
        }

        if(currentWait > getSeconds()) return;

        addWaitTime(OuttakeConstants.rotateIntakePivotWait);
        OuttakeStates.setActivateOuttakeState(ActivateOuttakeStates.rotateIntakePivot);
    }

    private void rotateIntakePivot() {


        if(currentWait > getSeconds()) return;

        addWaitTime(OuttakeConstants.rotateOuttakeArmWait);
        OuttakeStates.setActivateOuttakeState(ActivateOuttakeStates.rotateArmDown);
    }

    private void rotateOuttakePivot() {
        if(currentWait > getSeconds()) return;

        addWaitTime(OuttakeConstants.clawCloseWait);
        OuttakeStates.setActivateOuttakeState(ActivateOuttakeStates.closeClaw);
    }

    private void closeClaw() {
        if(currentWait > getSeconds()) return;

        OuttakeStates.setActivateOuttakeState(ActivateOuttakeStates.raiseSlides);
    }

    private void raiseSlides() {
        if(Math.abs(slideLogic.getSlidePosition() - OuttakeConstants.slidesClearIntakeHeight) > OuttakeConstants.slideTargetThreshold) return;

        OuttakeStates.setActivateOuttakeState(ActivateOuttakeStates.rotateArm);
    }

    private void rotateArm() {

        OuttakeStates.setActivateOuttakeState(ActivateOuttakeStates.idle);
    }

    private void idle(){
//        if(OuttakeStates.getVerticalSlideState() == VerticalSlideStates.highBasket)
//            OuttakeStates.setSampleReleaseButtonState(SampleReleaseButtonStates.flipArm);
    }



    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}
