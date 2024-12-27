package org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo.ExtendoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class AutoCloseLogic {
    private double currentWait = 0;
    private final ElapsedTime elapsedTime;
    private final SensorControl sensorControl;

    public AutoCloseLogic(ElapsedTime elapsedTime, SensorControl sensorControl) {
        this.elapsedTime = elapsedTime;
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
            case closeClaw:
                closeClaw();
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
        if(currentWait > elapsedTime.seconds()) return;
        addWaitTime(IntakeConstants.intakePushoutTime);
        IntakeStates.setAutoCloseStates(AutoCloseStates.ejectExtraSamples);
    }

    private void ejectExtraSamples() {
        if(currentWait > elapsedTime.seconds()) return;
        IntakeStates.setAutoCloseStates(AutoCloseStates.waitForCommand);
    }

    private void waitForCommand() {
        if(GlobalVariables.isAutonomous)
            IntakeStates.setAutoCloseStates(AutoCloseStates.waitToRetract);
    }

    private void waitToRetract() {
        if(IntakeStates.getExtendoState() != ExtendoStates.retracted) return;
        IntakeStates.setAutoCloseStates(AutoCloseStates.closeClaw);
        addWaitTime(IntakeConstants.clawCloseTime);
    }

    private void closeClaw() {
        if(currentWait > elapsedTime.seconds()) return;
        IntakeStates.setAutoCloseStates(AutoCloseStates.idle);
    }

    private void idle() {
        if(IntakeStates.getIntakeState() == SubsystemState.Run)
            IntakeStates.setAutoCloseStates(AutoCloseStates.checkColor);
    }

    private boolean isSampleDetected(){
        return sensorControl.isYellow() || sensorControl.isAllianceColor();
    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }
}
