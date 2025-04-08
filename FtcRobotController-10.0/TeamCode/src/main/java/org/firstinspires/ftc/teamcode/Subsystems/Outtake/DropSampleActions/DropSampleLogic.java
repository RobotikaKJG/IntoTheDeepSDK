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
            case openLock:
                openLock();
                break;
            case spinMotor:
                spinMotor();
                break;
            case waitToEject:
                waitToEject();
                break;
            case idle:
                idle();
                break;
        }
    }

    private void openLock() {
        OuttakeStates.setDropSampleState(DropSampleStates.spinMotor);
    }

    private void spinMotor() {
        addWaitTime(OuttakeConstants.ejectWait);
        OuttakeStates.setDropSampleState(DropSampleStates.waitToEject);
    }

    private void waitToEject() {
        if(getSeconds() < currentWait) return;
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
