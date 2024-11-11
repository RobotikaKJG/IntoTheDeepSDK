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
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeServoController;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeServoStates;

public class AutoClose implements IntakeStateChange {
    private final SensorControl sensorControl;
    private final SlideLogic slideLogic;
    private final ServoControl servoControl;
    private final MotorControl motorControl;
    private final IntakeController intakeController;
    private final ElapsedTime elapsedTime;
    private final EdgeDetection edgeDetection;
    private final OuttakeServoController outtakeServoController;
    private double currentWait = 0;
    private AutoCloseStopStates currentState = AutoCloseStopStates.idle;

    public AutoClose(SensorControl sensorControl, SlideLogic slideLogic, IntakeController intakeController, ElapsedTime elapsedTime, ServoControl servoControl, MotorControl motorControl, EdgeDetection edgeDetection, OuttakeServoController outtakeServoController) {
        this.sensorControl = sensorControl;
        this.slideLogic = slideLogic;
        this.intakeController = intakeController;
        this.elapsedTime = elapsedTime;
        this.servoControl = servoControl;
        this.motorControl = motorControl;
        this.edgeDetection = edgeDetection;
        this.outtakeServoController = outtakeServoController;
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
        currentState = AutoCloseStopStates.secureGoodSample;
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
            case pushToBack:
                pushToBack();
                break;
            case closeClaw:
                closeClaw();
                break;
            case waitToClose:
                waitToClose();
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
    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }

    private void waitForCommand() {
        if(edgeDetection.rising(IntakeConstants.motorButton))
        {
            currentState = AutoCloseStopStates.idle;
            intakeController.toggleIntakePower();
            intakeController.setIntaking(true);
            intakeController.setExtended(true);
            intakeController.setIntakeState(SubsystemState.Run);
        }
        if (!edgeDetection.rising(IntakeConstants.closeButton) && slideLogic.getSlidePosition() > 10) return;
        slideLogic.setSlideExtensionTarget(0);
        currentState = AutoCloseStopStates.pushToBack;
        //currentState = AutoCloseStopStates.closeClaw;
    }

    //For now it just runs on time, limit switch could be implemented to help, disabled
    private void pushToBack() {
        if(slideLogic.getSlidePosition() > 2) return;
        servoControl.setServoSpeed(0, IntakeConstants.servoSpeed/2);
        addWaitTime(IntakeConstants.pushToEndTime);
        currentState = AutoCloseStopStates.closeClaw;
    }

    private void closeClaw() {
        if(currentWait > elapsedTime.seconds()) return;
        servoControl.setServoSpeed(0, 0);
        outtakeServoController.setServoState(OuttakeServoStates.downClose);
        addWaitTime(IntakeConstants.clawCloseTime);
        currentState = AutoCloseStopStates.waitToClose;
    }

    private void waitToClose(){
        if(currentWait > elapsedTime.seconds()) return;
        intakeController.setIntakeState(SubsystemState.Idle);
    }
}
