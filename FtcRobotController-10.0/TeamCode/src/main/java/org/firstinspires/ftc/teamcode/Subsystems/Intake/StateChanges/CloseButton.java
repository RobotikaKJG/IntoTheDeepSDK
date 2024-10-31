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

public class CloseButton implements IntakeStateChange {


    private final SlideLogic slideLogic;
    private final MotorControl motorControl;
    private final IntakeController intakeController;
    private final ElapsedTime elapsedTime;
    private final EdgeDetection edgeDetection;
    private final ServoControl servoControl;
    private double currentWait = 0;

    public CloseButton(SlideLogic slideLogic, IntakeController intakeController, ElapsedTime elapsedTime, ServoControl servoControl, MotorControl motorControl, EdgeDetection edgeDetection) {
        this.slideLogic = slideLogic;
        this.intakeController = intakeController;
        this.elapsedTime = elapsedTime;
        this.servoControl = servoControl;
        this.motorControl = motorControl;
        this.edgeDetection = edgeDetection;
    }

    @Override
    public boolean shouldBeStopping() {
        return edgeDetection.rising(IntakeConstants.closeButton);
    }

    @Override
    public void initialiseStop() {
        slideLogic.setSlideExtensionTarget(0);
        if (intakeController.getIntaking())
            motorControl.setMotorSpeed(MotorConstants.intake, -IntakeConstants.intakeSpeed);
        addWaitTime(IntakeConstants.intakePushoutTime);
    }

    @Override
    public void stop() {
        if(!slideLogic.slidesBottomReached() || (currentWait > elapsedTime.seconds())) return;

        motorControl.setMotorSpeed(MotorConstants.intake, 0);
        servoControl.setServoSpeed(0,0);
        intakeController.setIntakeState(SubsystemState.Idle);
    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }
}
