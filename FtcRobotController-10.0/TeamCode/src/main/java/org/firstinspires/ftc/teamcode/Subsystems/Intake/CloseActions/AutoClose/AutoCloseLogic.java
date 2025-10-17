package org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.SampleEjection.SampleEjectionStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Stall.StallStates;

public class AutoCloseLogic {
    private double currentWait = 0;
    private final SensorControl sensorControl;
    private final MotorControl motorControl;

    public AutoCloseLogic(SensorControl sensorControl, MotorControl motorControl) {
        this.sensorControl = sensorControl;
        this.motorControl = motorControl;
    }

    public void update() {
//        System.out.println(IntakeStates.getMotorState());
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

    private void checkColor() { // ALL THE LOGIC HERE SHOULD GO SOMEWHERE SEPARATE, NOTE
//        System.out.println(IntakeStates.getMotorState());
        if(motorControl.isOverCurrent(MotorConstants.intake) && IntakeStates.getStallState() == StallStates.idle)
            IntakeStates.setStallState(StallStates.eject);

        if(!isSampleDetected()) return;

        if(isWrongSampleDetected() && ejectionIdle())
        {
            IntakeStates.setSampleEjectionState(SampleEjectionStates.stopMotor);
        }

        if(!isCorrectSampleDetected()) return;
        if(!securingAllowed()) return;

        IntakeStates.setAutoCloseStates(AutoCloseStates.secureGoodSample);
//        System.out.println(sensorControl.currentRed);
//        System.out.println(sensorControl.currentGreen);
//        System.out.println(sensorControl.currentBlue);
//        System.out.println("bad");
        if(GlobalVariables.isAutonomous)
            addWaitTime(IntakeConstants.secureSampleAutonTime);
        else
            addWaitTime(IntakeConstants.secureSampleTime);
    }

    private boolean securingAllowed() {
        return IntakeStates.getSampleEjectionState() == SampleEjectionStates.idle ||
                IntakeStates.getSampleEjectionState() == SampleEjectionStates.closeLatch ||
                IntakeStates.getSampleEjectionState() == SampleEjectionStates.startMotor;

    }

    private static boolean ejectionIdle() {
        return IntakeStates.getSampleEjectionState() == SampleEjectionStates.idle;
    }

    private void secureGoodSample() {
        if(currentWait > getSeconds()) return;
//        if(!isSampleDetected()) return;
        if(GlobalVariables.isAutonomous)
            addWaitTime(IntakeConstants.intakeAutonomousPushoutTime);
        else
            addWaitTime(IntakeConstants.intakePushoutTime);
        //if(GlobalVariables.isAutonomous)
        //    IntakeStates.setAutoCloseStates(AutoCloseStates.waitForCommand);
        //else
        IntakeStates.setAutoCloseStates(AutoCloseStates.ejectExtraSamples);
    }
    private void ejectExtraSamples() {
        if(currentWait > getSeconds()) return;
//        if(!isSampleDetected()) return;
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
        if(IntakeStates.getMotorState() == IntakeMotorStates.forward) {
            sensorControl.resetColor();
            IntakeStates.setAutoCloseStates(AutoCloseStates.checkColor);
        }
    }

    private boolean isSampleDetected(){
        return sensorControl.getDistance() < 59;
    }

    private boolean isCorrectSampleDetected(){
        System.out.println("Yellow: " + sensorControl.isYellow());
        System.out.println("Alliance color: " + sensorControl.isAllianceColor());

        return sensorControl.isYellow() || sensorControl.isAllianceColor();
    }

    private boolean isWrongSampleDetected() {
        return sensorControl.isOtherAllianceColor();
    }

    private void addWaitTime(double waitTime) {
        currentWait = getSeconds() + waitTime;
    }

    private double getSeconds() {
        return System.currentTimeMillis() / 1_000.0;
    }
}
