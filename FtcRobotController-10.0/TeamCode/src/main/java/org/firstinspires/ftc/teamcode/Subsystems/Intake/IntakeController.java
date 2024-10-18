package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.RobotSubsystemController;

public class IntakeController implements RobotSubsystemController {
    private final IntakeMotorTrigger intakeMotorTrigger = new IntakeMotorTrigger();
    private final IntakeExtendoTrigger intakeExtendoTrigger = new IntakeExtendoTrigger();
    private final EdgeDetection edgeDetection;
    private final MotorControl motorControl;
    private final SlideLogic slideLogic;
    private SubsystemState intakeState = SubsystemState.Idle;
    private boolean extended = false;
    private boolean intaking = false;

    public IntakeController(MotorControl motorControl, EdgeDetection edgeDetection, SlideLogic slideLogic) {
        this.edgeDetection = edgeDetection;
        this.motorControl = motorControl;
        this.slideLogic = slideLogic;
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
        if(intaking)
            motorControl.setMotorSpeed(MotorConstants.intake, 1);
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
            motorControl.setMotorSpeed(MotorConstants.intake, 1);
        else
            motorControl.setMotorSpeed(MotorConstants.intake, 0);
    }

    private boolean shouldBeStopping()
    {
        return (!intaking && !extended) || edgeDetection.rising(IntakeConstants.closeButton);
    }

    private void initialiseStop()
    {
        slideLogic.setSlideExtensionTarget(0);
        slideLogic.setMaxSpeed(0.7);
        motorControl.setMotorSpeed(MotorConstants.intake, 0);
        extended = false;
        intaking = false;
        intakeState = SubsystemState.Stop;
    }

    @Override
    public void stop() {
        slideLogic.updateSlides();
        if(slideLogic.slidesBottomReached())
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
}
