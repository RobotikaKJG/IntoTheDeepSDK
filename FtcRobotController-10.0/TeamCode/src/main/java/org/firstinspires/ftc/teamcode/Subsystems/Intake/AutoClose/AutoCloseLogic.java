package org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoClose;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class AutoCloseLogic {
    private double currentWait = 0;
    private final SensorControl sensorControl;

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
            case idle:
                idle();
                break;
        }
    }

    private void checkColor() {
        if(!isSampleDetected()) return;
        IntakeStates.setAutoCloseState(AutoCloseStates.securedGoodSample);
        addWaitTime(IntakeConstants.secureSampleWait);
    }
    private void securedGoodSample() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setAutoCloseState(AutoCloseStates.idle);
    }
    private void idle() {
        if(IntakeStates.getMotorState() == IntakeMotorStates.forward) {
            sensorControl.resetColor();
            IntakeStates.setAutoCloseState(AutoCloseStates.checkColor);
        }
    }

    private boolean isSampleDetected(){
        if(sensorControl.getDistance() > 60) // add to constants, NOTE
            return false;
        return sensorControl.isAllianceColor();
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}
