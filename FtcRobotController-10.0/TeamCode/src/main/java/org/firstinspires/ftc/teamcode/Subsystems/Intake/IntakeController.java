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

    public IntakeController(MotorControl motorControl, EdgeDetection edgeDetection, SlideLogic slideLogic, SensorControl sensorControl, ElapsedTime elapsedtime, ServoControl servoControl) {
        this.edgeDetection = edgeDetection;
        this.motorControl = motorControl;
        this.slideLogic = slideLogic;
        this.sensorControl = sensorControl;
        this.elapsedTime = elapsedtime;
        this.servoControl = servoControl;
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
            motorControl.setMotorSpeed(MotorConstants.intake, -1);
            servoControl.setServoSpeed(0, 1);
        }
        if(extended)
            slideLogic.setSlideExtensionTarget(IntakeConstants.slideExtensionStep);
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
            slideLogic.addSlideExtension(IntakeConstants.slideExtensionStep);

        else if (edgeDetection.rising(IntakeConstants.backButton))
            slideLogic.addSlideExtension(-IntakeConstants.slideExtensionStep);
    }
    private void toggleMotor()
    {
        intaking = !intaking;
        if(intaking)
            motorControl.setMotorSpeed(MotorConstants.intake, -1);
        else
            motorControl.setMotorSpeed(MotorConstants.intake, 0);
    }

    private boolean shouldBeStopping()
    {
        return (!intaking && !extended) || edgeDetection.rising(IntakeConstants.closeButton)||sensorControl.isColorMatch(IntakeConstants.targetColor, IntakeConstants.threshold);

    }

    private void initialiseStop()
    {
        slideLogic.setSlideExtensionTarget(0);
        slideLogic.setMaxSpeed(0.7);
        motorControl.setMotorSpeed(MotorConstants.intake, 1);
        servoControl.setServoSpeed(0, 0);
        extended = false;
        intaking = false;
        addWaitTime(IntakeConstants.intakePushoutTime);
        intakeState = SubsystemState.Stop;
    }

    @Override
    public void stop() {
        slideLogic.updateSlides();
        if(!(/*slideLogic.slidesBottomReached() &&*/ currentWait < elapsedTime.seconds()))
            return;
        motorControl.setMotorSpeed(MotorConstants.intake, 0);
        intakeState = SubsystemState.Idle;
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
    void setIntakeState(SubsystemState intakeState) {
        this.intakeState = intakeState;
    }
}
