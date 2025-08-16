package org.firstinspires.ftc.teamcode.Subsystems.Intake.AutoEject;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;

public class AutoEjectLogic {
    private double currentWait = 0;
    private final SensorControl sensorControl;

    public AutoEjectLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        switch (IntakeStates.getAutoEjectState()) {
            case sampleOut:
                sampleOut();
                break;
            case close:
                close();
                break;
            case idle:
                idle();
                break;
        }
    }

    private void sampleOut() {
        if(isSampleDetected()) return;
        IntakeStates.setAutoEjectState(AutoEjectStates.close);
        addWaitTime(IntakeConstants.sampleEjectWait);
    }
    private void close() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setAutoEjectState(AutoEjectStates.idle);
    }
    private void idle() {
        if(IntakeStates.getMotorState() == IntakeMotorStates.forward && IntakeStates.getPivotState() == PivotStates.up) {
            sensorControl.resetColor();
            IntakeStates.setAutoEjectState(AutoEjectStates.sampleOut);
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
        return System.currentTimeMillis() / 1000.0;
    }
}

