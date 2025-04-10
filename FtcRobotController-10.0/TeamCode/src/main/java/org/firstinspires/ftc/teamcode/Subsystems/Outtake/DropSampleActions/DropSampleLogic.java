package org.firstinspires.ftc.teamcode.Subsystems.Outtake.DropSampleActions;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class DropSampleLogic {

    private final SlideLogic slideLogic;
    private double currentWait = 0;

    public DropSampleLogic(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void update()
    {
        switch(OuttakeStates.getDropSampleState())
        {
            case activate:
                activate();
                break;
            case raiseSlides:
                raiseSlides();
                break;
            case flipArm:
                flipArm();
                break;
            case release:
                release();
                break;
            case flipBack:
                flipBack();
                break;
            case retractSlides:
                retractSlides();
                break;
            case waitToRetract:
                waitToRetract();
                break;
            case idle:
                idle();
                break;
        }
    }

    private void activate(){
        addWaitTime(0.2);
        OuttakeStates.setDropSampleState(DropSampleStates.raiseSlides);
    }

    private void raiseSlides() {
        if(currentWait > getSeconds()) return;
        addWaitTime(1.3);
        OuttakeStates.setDropSampleState(DropSampleStates.flipArm);
    }

    private void flipArm() {
        if(currentWait > getSeconds()) return;
        addWaitTime(0.2);
        OuttakeStates.setDropSampleState(DropSampleStates.release);
    }

    private void release() {
        if(currentWait > getSeconds()) return;
        addWaitTime(0.5);
        OuttakeStates.setDropSampleState(DropSampleStates.flipBack);
    }

    private void flipBack() {
        if(currentWait > getSeconds()) return;
        addWaitTime(0.5);
        OuttakeStates.setDropSampleState(DropSampleStates.retractSlides);
    }

    private void retractSlides() {
        if(currentWait > getSeconds()) return;
        OuttakeStates.setDropSampleState(DropSampleStates.waitToRetract);
    }

    private void waitToRetract(){
        if(OuttakeStates.getVerticalSlideState() != VerticalSlideStates.closed) return;
        OuttakeStates.setDropSampleState(DropSampleStates.idle);
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
