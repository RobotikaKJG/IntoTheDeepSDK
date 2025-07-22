package org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions;

import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;

public class CloseLogic {
    private double currentWait = 0;
    private final SensorControl sensorControl;

    public CloseLogic(SensorControl sensorControl) {
        this.sensorControl = sensorControl;
    }

    public void update() {
        switch (IntakeStates.getCloseStates()) {
            case checkColor:
                checkColor();
                break;
            case secureGoodSample:
                secureGoodSample();
                break;
            case waitForCommand:
                waitForCommand();
                break;
            case pivot:
                pivot();
                break;
            case waitToPivot:
                waitToPivot();
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
        IntakeStates.setCloseStates(CloseStates.secureGoodSample);
        if(GlobalVariables.isAutonomous)
            addWaitTime(IntakeConstants.secureSampleAutonTime);
        else
            addWaitTime(IntakeConstants.secureSampleTime);
    }
    private void secureGoodSample() {
        if(currentWait > getSeconds()) return;
//        if(!isSampleDetected()) return;
        if(GlobalVariables.isAutonomous)
            addWaitTime(IntakeConstants.intakeAutonomousPushoutTime);
        else
            addWaitTime(IntakeConstants.intakePushoutTime);
        //if(GlobalVariables.isAutonomous)
        //    IntakeStates.setCloseStates(CloseStates.waitForCommand);
        //else
        IntakeStates.setCloseStates(CloseStates.waitForCommand);
    }

    private void waitForCommand() {
        if(GlobalVariables.isAutonomous)
            IntakeStates.setCloseStates(CloseStates.waitToPivot);
    }

    private void pivot(){
        addWaitTime(IntakeConstants.pivotWaitTime);
    }

    private void waitToPivot() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setCloseStates(CloseStates.waitToRetract);
    }


    private void waitToRetract() {
        if(IntakeStates.getExtendoState() != ExtendoStates.retracted) return;
        IntakeStates.setCloseStates(CloseStates.closeSampleClaw);
        addWaitTime(IntakeConstants.sampleClawCloseTime);
    }
    private void closeSampleClaw() {
        if(currentWait > getSeconds()) return;
        IntakeStates.setCloseStates(CloseStates.idle);
    }
    private void idle() {
        if(IntakeStates.getMotorState() == IntakeMotorStates.forward) {
            sensorControl.resetColor();
            IntakeStates.setCloseStates(CloseStates.checkColor);
        }
    }

    private boolean isSampleDetected(){
        if(sensorControl.getDistance() > 60) // add to constants, NOTE
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
