package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.DcMotor;
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
    private final SensorControl sensorControl;
    private final ServoControl servoControl;
    private SubsystemState intakeState = SubsystemState.Idle;
    private boolean extended = false;
    private boolean intaking = false;
    private double currentWait;
    private final ElapsedTime elapsedTime;
    private final IntakeStateController intakeStateController;

    public IntakeController(MotorControl motorControl, EdgeDetection edgeDetection, SlideLogic slideLogic, SensorControl sensorControl, ElapsedTime elapsedtime, ServoControl servoControl) {
        this.edgeDetection = edgeDetection;
        this.motorControl = motorControl;
        this.slideLogic = slideLogic;
        this.sensorControl = sensorControl;
        this.elapsedTime = elapsedtime;
        this.servoControl = servoControl;
        this.intakeStateController = new IntakeStateController(motorControl, servoControl, sensorControl, edgeDetection, slideLogic, this, elapsedTime);
        //this.motorControl.setMotorMode(MotorConstants.extendo, DcMotor.RunMode.RUN_TO_POSITION);
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

        slideLogic.updateSlides();

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
        //return (!intaking && !extended) || edgeDetection.rising(IntakeConstants.closeButton)||sensorControl.isColorMatch(IntakeConstants.targetColor, IntakeConstants.threshold);
        return intakeStateController.shouldBeStopping();
    }

    private void initialiseStop()
    {
//        slideLogic.setSlideExtensionTarget(0);
//        motorControl.setMotorSpeed(MotorConstants.intake, 1);
//        servoControl.setServoSpeed(0, 0);
        intakeStateController.initialiseStop();
        extended = false;
        intaking = false;
//        addWaitTime(IntakeConstants.intakePushoutTime);
        intakeState = SubsystemState.Stop;
    }

    @Override
    public void stop() {
        slideLogic.updateSlides();
//        if(!(/*slideLogic.slidesBottomReached() &&*/ currentWait < elapsedTime.seconds()))
//            return;
//        motorControl.setMotorSpeed(MotorConstants.intake, 0);
//        intakeState = SubsystemState.Idle;
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
    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }
    public void setIntakeState(SubsystemState intakeState) {
        this.intakeState = intakeState;
    }
    void setExtended(boolean extended) {
        this.extended = extended;
    }
    void setIntaking(boolean intaking) {
        this.intaking = intaking;
    }
    public boolean getIntaking()
    {
        return intaking;
    }
}
