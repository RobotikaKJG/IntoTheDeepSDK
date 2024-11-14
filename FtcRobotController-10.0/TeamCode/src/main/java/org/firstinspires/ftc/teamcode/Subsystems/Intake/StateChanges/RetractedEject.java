package org.firstinspires.ftc.teamcode.Subsystems.Intake.StateChanges;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeController;

public class RetractedEject implements IntakeStateChange {
    private final SensorControl sensorControl;
    private final SlideLogic slideLogic;
    private final ServoControl servoControl;
    private final MotorControl motorControl;
    private final IntakeController intakeController;
    private final ElapsedTime elapsedTime;
    private double currentWait = 0;
    private AutoCloseStopStates currentState = AutoCloseStopStates.idle;

    public RetractedEject(SensorControl sensorControl, SlideLogic slideLogic, IntakeController intakeController, ElapsedTime elapsedTime, ServoControl servoControl, MotorControl motorControl) {
        this.sensorControl = sensorControl;
        this.slideLogic = slideLogic;
        this.intakeController = intakeController;
        this.elapsedTime = elapsedTime;
        this.servoControl = servoControl;
        this.motorControl = motorControl;
    }

    @Override
    public boolean shouldBeStopping() { //THIS MAY CAUSE ISSUES, REMEMBER IF DEBUGGING
        return sensorControl.isColorMatch(IntakeConstants.otherAllianceColor, IntakeConstants.otherAllianceThreshold) && !sensorControl.isColorMatch(IntakeConstants.yellow, IntakeConstants.yellowThreshold);
    }

    @Override
    public void initialiseStop() {
        motorControl.setMotorSpeed(MotorConstants.intake, -IntakeConstants.intakeSpeed);
        servoControl.setServoSpeed(0,-IntakeConstants.servoSpeed);
        addWaitTime(IntakeConstants.intakePushoutTime);
    }

    @Override
    public void stop() {
        if(currentWait > elapsedTime.seconds()) return;

        motorControl.setMotorSpeed(MotorConstants.intake, 0);
        servoControl.setServoSpeed(0,0);
        intakeController.setIntakeState(SubsystemState.Start);
    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }
}
