package org.firstinspires.ftc.teamcode.Subsystems.Intake.SampleEjection;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class SampleEjectionLogic {

    private double currentWait = 0;

    public void update(){
        switch(IntakeStates.getSampleEjectionState())
        {
            case stopMotor:
                stopMotor();
                break;
            case openLatch:
                openLatch();
                break;
            case startMotor:
                startMotor();
                break;
            case closeLatch:
                closeLatch();
                break;
            case idle:
                break;
        }
    }

    private void stopMotor() {
        IntakeStates.setSampleEjectionState(SampleEjectionStates.openLatch);
        addWaitTime(0.1);
    }

    private void openLatch() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setSampleEjectionState(SampleEjectionStates.startMotor);
        if(GlobalVariables.isAutonomous)
            addWaitTime(0.1);
        else
            addWaitTime(0.2);
    }

    private void startMotor() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setSampleEjectionState(SampleEjectionStates.closeLatch);
        addWaitTime(1);

    }

    private void closeLatch() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setSampleEjectionState(SampleEjectionStates.idle);
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}
