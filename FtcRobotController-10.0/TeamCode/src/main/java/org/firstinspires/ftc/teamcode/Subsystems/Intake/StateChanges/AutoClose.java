package org.firstinspires.ftc.teamcode.Subsystems.Intake.StateChanges;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeController;

public class AutoClose implements IntakeStateChange {
    private final SensorControl sensorControl;
    private final SlideLogic slideLogic;
    private final ServoControl servoControl;
    private final MotorControl motorControl;
    private final IntakeController intakeController;
    private final ElapsedTime elapsedTime;
    private final EdgeDetection edgeDetection;
    private double currentWait = 0;
    private AutoCloseStopStates currentState = AutoCloseStopStates.idle;

    public AutoClose(SensorControl sensorControl, SlideLogic slideLogic, IntakeController intakeController, ElapsedTime elapsedTime, ServoControl servoControl, MotorControl motorControl, EdgeDetection edgeDetection) {
        this.sensorControl = sensorControl;
        this.slideLogic = slideLogic;
        this.intakeController = intakeController;
        this.elapsedTime = elapsedTime;
        this.servoControl = servoControl;
        this.motorControl = motorControl;
        this.edgeDetection = edgeDetection;
    }

    @Override
    public boolean shouldBeStopping() {
        return isColorMatch();
    }

    private boolean isColorMatch()
    {
        return sensorControl.isColorMatch(IntakeConstants.yellow, IntakeConstants.threshold) || sensorControl.isColorMatch(IntakeConstants.allianceColor, IntakeConstants.threshold);
    }

    @Override
    public void initialiseStop() {
        addWaitTime(IntakeConstants.secureSampleTime);
        currentState = AutoCloseStopStates.waitForCommand;
    }

    @Override
    public void stop() {
        switch (currentState) {
            case secureGoodSample:
                secureGoodSample();
                break;
            case ejectExtraSamples:
                ejectExtraSamples();
                break;
            case waitForCommand:
                waitForCommand();
                break;
        }

    }

    private void secureGoodSample(){
        if(currentWait > elapsedTime.seconds()) return;
        servoControl.setServoSpeed(0, 0);
        motorControl.setMotorSpeed(MotorConstants.intake, -IntakeConstants.intakeSpeed);
        addWaitTime(IntakeConstants.intakePushoutTime);
        currentState = AutoCloseStopStates.ejectExtraSamples;
    }
    private void ejectExtraSamples(){
        if(currentWait > elapsedTime.seconds()) return;
        motorControl.setMotorSpeed(MotorConstants.intake, 0);
        currentState = AutoCloseStopStates.waitForCommand;
        intakeController.setIntakeState(SubsystemState.Idle);
    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }

    private void waitForCommand() {
        if (!edgeDetection.rising(IntakeConstants.closeButton)) return;
        slideLogic.setSlideExtensionTarget(0);
        currentState = AutoCloseStopStates.idle;
    }
}
