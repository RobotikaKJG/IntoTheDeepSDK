package org.firstinspires.ftc.teamcode.Subsystems.Intake.Hang;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;

public class HangLogic {
    private double currentWait = 0;
    private boolean wasIfCalled = false;

    public HangLogic() {
    }

    public void update(){
        switch (IntakeStates.getHangState()) {
            case pivotUp:
                pivotUp();
                break;
            case extendSlides:
                extendSlides();
                break;
            case retractSlides:
                retractSlides();
                break;
            case pivotUpFully:
                pivotUpFully();
                break;
        }
    }

    private void pivotUp() {
        if (!wasIfCalled)
        {
            addWaitTime(1);
            wasIfCalled = true;
        }
        if(currentWait > getSeconds()) return;
        wasIfCalled = false;

        addWaitTime(1);
        IntakeStates.setHangState(HangStates.extendSlides);
    }

    private void extendSlides() {
        if(currentWait > getSeconds()) return;

        IntakeStates.setHangState(HangStates.waitForButton);
    }

    private void retractSlides() {
        if (!wasIfCalled)
        {
            addWaitTime(1);
            wasIfCalled = true;
        }
        if(currentWait > getSeconds()) return;
        wasIfCalled = false;

        IntakeStates.setHangState(HangStates.pivotUpFully);
    }

    private void pivotUpFully() {
        IntakeStates.setHangState(HangStates.idle);
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1000.0;
    }
}
