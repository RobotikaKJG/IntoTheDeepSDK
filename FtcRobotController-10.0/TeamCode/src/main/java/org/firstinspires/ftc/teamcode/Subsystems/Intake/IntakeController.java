package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.RobotSubsystemController;

public class IntakeController implements RobotSubsystemController {
    private final IntakeMotorTrigger intakeMotorTrigger = new IntakeMotorTrigger();
    private final IntakeExtendoTrigger intakeExtendoTrigger = new IntakeExtendoTrigger();
    private final EdgeDetection edgeDetection;
    private final MotorControl motorControl;
    private final SlideLogic slideLogic;
    private final ServoControl servoControl;
    private SubsystemState intakeState = SubsystemState.Idle;
    private boolean extended = false;
    private boolean intaking = false;
    private final IntakeStateController intakeStateController;

    public IntakeController(MotorControl motorControl, EdgeDetection edgeDetection, SlideLogic slideLogic, SensorControl sensorControl, ElapsedTime elapsedtime, ServoControl servoControl) {
        this.edgeDetection = edgeDetection;
        this.motorControl = motorControl;
        this.slideLogic = slideLogic;
        this.servoControl = servoControl;
        this.intakeStateController = new IntakeStateController(motorControl, servoControl, sensorControl, edgeDetection, slideLogic, this, elapsedtime);
    }
    @Override
    public void updateState() {
        switch (intakeState) {
            case Start:
                start();
                break;
            case Run:
                run();
                break;
            case Stop:
                stop();
                break;
            case Idle:
                idle();
                break;
        }
    }

    @Override
    public void start() {
        if(intaking) {
            motorControl.setMotorSpeed(MotorConstants.intake, IntakeConstants.intakeSpeed);
            servoControl.setServoSpeed(0, IntakeConstants.servoSpeed);
        }
        if(extended)
            slideLogic.stepUp();
        slideLogic.setMaxSpeed(1);
        intakeState = SubsystemState.Run;
    }

    @Override
    public void run() {
        gamepadActions();

        if (!shouldBeStopping()) return;

        initialiseStop();
    }

    private void gamepadActions() {
        if (edgeDetection.rising(IntakeConstants.motorButton))
            toggleMotor();

        if (edgeDetection.rising(IntakeConstants.forwardButton))
            slideLogic.stepUp();

        else if (edgeDetection.rising(IntakeConstants.backButton))
            slideLogic.stepDown();
    }
    private void toggleMotor()
    {
        intaking = !intaking;
        if(intaking)
            motorControl.setMotorSpeed(MotorConstants.intake, IntakeConstants.intakeSpeed);
        else
            motorControl.setMotorSpeed(MotorConstants.intake, 0);
    }

    private boolean shouldBeStopping()
    {
        return intakeStateController.shouldBeStopping();
    }

    private void initialiseStop()
    {
        intakeStateController.initialiseStop();
        extended = false;
        intaking = false;
        intakeState = SubsystemState.Stop;
    }

    @Override
    public void stop() {
        intakeStateController.stop();
    }

    @Override
    public void idle() {
        if (edgeDetection.rising(intakeMotorTrigger.getTrigger()))
            intaking = true;
        else if (edgeDetection.rising(intakeExtendoTrigger.getTrigger()))
            extended = true;

        if(intaking || extended)
            intakeState = SubsystemState.Start;
    }
    public void setIntakeState(SubsystemState intakeState) {
        this.intakeState = intakeState;
    }
    public boolean getIntaking()
    {
        return intaking;
    }
}
