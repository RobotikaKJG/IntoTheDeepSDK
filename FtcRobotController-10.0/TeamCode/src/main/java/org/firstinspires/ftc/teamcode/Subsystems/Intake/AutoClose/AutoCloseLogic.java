package org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

public class AutoCloseLogic {
    private double currentWait = 0;
    private final SensorControl sensorControl;
    private boolean wasIfCalled;

    public AutoCloseLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        switch (IntakeStates.getAutoCloseState()) {
            case checkColor:
                checkColor();
                break;
            case securedGoodSample:
                securedGoodSample();
                break;
            case waitForRetractConfirmation:
                waitForRetractConfirmation();
                break;
            case waitToRetract:
                waitToRetract();
                break;
            case waitForPivotConfirmation:
                waitForPivotConfirmation();
                break;
            case pivot:
                pivot();
                break;
            case waitForReleaseConfirmation:
                waitForReleaseConfirmation();
                break;
            case openLatch:
                openLatch();
                break;
            case release:
                release();
                break;
            case pivotDown:
                pivotDown();
                break;
            case idle:
                idle();
                break;
        }
    }

    private void openLatch() {
        if(!wasIfCalled)
        {
            addWaitTime(0.3);
            wasIfCalled = true;
        }
        if(currentWait > getSeconds()) return;

        wasIfCalled = false;
        IntakeStates.setAutoCloseState(AutoCloseStates.release);
    }

    private void waitForRetractConfirmation() {
    }

    private void waitToRetract() {
        if(!wasIfCalled)
        {
            addWaitTime(0.4);
            wasIfCalled = true;
        }
        if(currentWait > getSeconds()) return;

        wasIfCalled = false;
        IntakeStates.setAutoCloseState(AutoCloseStates.pivot);
    }

    private void waitForPivotConfirmation() {

    }

    private void pivot() {
        if(!wasIfCalled)
        {
            addWaitTime(1);
            wasIfCalled = true;
        }
        if(currentWait > getSeconds()) return;

        wasIfCalled = false;
        IntakeStates.setAutoCloseState(AutoCloseStates.waitForReleaseConfirmation);
    }

    private void waitForReleaseConfirmation() {
    }

    private void release() {
        if(!wasIfCalled)
        {
            addWaitTime(0.4);
            wasIfCalled = true;
        }
        if(currentWait > getSeconds()) return;

        wasIfCalled = false;
        IntakeStates.setAutoCloseState(AutoCloseStates.pivotDown);
    }

    private void pivotDown() {
        if(!wasIfCalled)
        {
            addWaitTime(1);
            wasIfCalled = true;
        }
        if(currentWait > getSeconds()) return;

        wasIfCalled = false;
        IntakeStates.setAutoCloseState(AutoCloseStates.idle);
    }

    private void checkColor() {
        if(!isSampleDetected()) return;
        IntakeStates.setAutoCloseState(AutoCloseStates.securedGoodSample);
        addWaitTime(IntakeConstants.secureSampleWait);
    }

    private void securedGoodSample() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setAutoCloseState(AutoCloseStates.waitForRetractConfirmation);
    }
    private void idle() {
        if(IntakeStates.getMotorState() == IntakeMotorStates.forward && IntakeStates.getArmSlideState() == ArmSlideStates.extended) {
            sensorControl.resetColor();
            IntakeStates.setAutoCloseState(AutoCloseStates.checkColor);
        }
    }

    private boolean isSampleDetected(){
        if(sensorControl.getDistance() > 90) // add to constants, NOTE
            return false;
        return sensorControl.isAllianceColor();
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1000.0;
    }
}
