package org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose;

import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class AutoCloseLogic {
    private double currentWait = 0;
    private final SensorControl sensorControl;

    public AutoCloseLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        switch (IntakeStates.getAutoCloseStates()) {
            case checkColor:
                checkColor();
                break;
            case secureGoodSample:
                secureGoodSample();
                break;
            case ejectExtraSamples:
                ejectExtraSamples();
                break;
            case waitForCommand:
                waitForCommand();
                break;
            case waitToRetract:
                waitToRetract();
                break;
            case closeSampleClaw:
                closeSampleClaw();
                break;
            case idle:
                idle();
                break;
        }
    }

    private void checkColor() {
        if(!isSampleDetected()) return;
        IntakeStates.setAutoCloseStates(AutoCloseStates.secureGoodSample);
        addWaitTime(IntakeConstants.secureSampleTime);
    }

    private void secureGoodSample() {
        if(currentWait > getSeconds()) return;
        addWaitTime(IntakeConstants.intakePushoutTime);
        if(GlobalVariables.isAutonomous)
            IntakeStates.setAutoCloseStates(AutoCloseStates.waitForCommand);
        else
            IntakeStates.setAutoCloseStates(AutoCloseStates.ejectExtraSamples);
    }

    private void ejectExtraSamples() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setAutoCloseStates(AutoCloseStates.waitForCommand);
    }

    private void waitForCommand() {
        if(GlobalVariables.isAutonomous)
            IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
    }

    private void waitToRetract() {
        if(IntakeStates.getExtendoState() != ExtendoStates.retracted) return;
        IntakeStates.setAutoCloseStates(AutoCloseStates.closeSampleClaw);
        addWaitTime(IntakeConstants.sampleClawCloseTime);
    }

    private void closeSampleClaw() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setAutoCloseStates(AutoCloseStates.idle);
    }

    private void idle() {
        if(IntakeStates.getIntakeState() == SubsystemState.Run) {
            sensorControl.resetColor();
            IntakeStates.setAutoCloseStates(AutoCloseStates.checkColor);
        }
    }

    private boolean isSampleDetected(){
        if(sensorControl.getDistance() > 70)
            return false;
        return sensorControl.isYellow() || sensorControl.isAllianceColor();
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}
