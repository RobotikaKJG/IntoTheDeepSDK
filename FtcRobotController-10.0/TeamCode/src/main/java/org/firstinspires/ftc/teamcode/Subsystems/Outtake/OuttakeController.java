package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.Enums.StateSwitch;
import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.RobotSubsystemController;

public class OuttakeController implements RobotSubsystemController {

    private final EdgeDetection edgeDetection;
    private final OuttakeTrigger outtakeTrigger = new OuttakeTrigger();
    private final SlideControl slideControl;
    private final OuttakeServoControl outtakeServoControl;
    private SubsystemState outtakeState = SubsystemState.Idle;
    private final OuttakeDependencies outtakeDependencies;
    private final ElapsedTime elapsedTime = new ElapsedTime();
    private double currentWait;
    private boolean wasIfCalled = false;
    private final Telemetry telemetry;

    public OuttakeController(OuttakeDependencies outtakeDependencies, EdgeDetection edgeDetection, Telemetry telemetry) {
        this.slideControl = outtakeDependencies.slideControl;
        OuttakeSlideProperties outtakeSlideProperties = new OuttakeSlideProperties();
        slideControl.setSlideProperties(outtakeSlideProperties);
        this.outtakeServoControl = outtakeDependencies.outtakeServoControl;
        this.edgeDetection = edgeDetection;
        this.outtakeDependencies = outtakeDependencies;
        this.telemetry = telemetry;
    }

    @Override
    public void updateState() {
        switch (outtakeState) {
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
        slideControl.setSlideExtensionTarget(OuttakeConstants.slideCurrentMinExtension);
        outtakeServoControl.checkReleaseServoPos();
        outtakeState = SubsystemState.Run;
    }

    @Override
    public void run() {
        gamepadActions();

        slideControl.updateSlides();

        //needs to go after to receive position update
        openServo();

        if (!shouldBeStopping()) return;
        initialiseStop();
    }

    private boolean shouldBeStopping() {
        return slideControl.getSlideExtensionTarget() < OuttakeConstants.slideCurrentMinExtension || edgeDetection.rising(OuttakeConstants.closeButton);
    }

    private void gamepadActions() {
        if (edgeDetection.rising(OuttakeConstants.releaseButton))
            outtakeServoControl.controlReleaseServo();

        if (edgeDetection.rising(OuttakeConstants.upButton))
            slideControl.addSlideExtension(OuttakeConstants.slideExtensionStep);

        else if (edgeDetection.rising(OuttakeConstants.downButton))
            slideControl.addSlideExtension(-OuttakeConstants.slideExtensionStep);
    }

    public void openServo() {
        if (outtakeServoControl.isServoOpen())
            return;
        int motorPos = slideControl.getCurrentMotorPositionAvg();
        if (motorPos >= OuttakeConstants.outtakeServoOpenHeight)
            outtakeServoControl.turnServo(StateSwitch.upOn);
    }

    public void initialiseStop() {
        outtakeState = SubsystemState.Stop;
        outtakeServoControl.turnServo(StateSwitch.downOn);
        addWaitTime(OuttakeConstants.outtakeServoCloseWait);
    }

    @Override
    public void stop() {
        if (currentWait > elapsedTime.seconds()) {
            slideControl.updateSlides();
            return;
        }

        if(!wasIfCalled)
          {
          slideControl.setSlideExtensionTarget(0);
          wasIfCalled = true;
          }
        //slideControl.updateSlides();
        ensureMinSpeed();

        if (!slideControl.slidesBottomReached()) return;
        wasIfCalled = false;
        outtakeState = SubsystemState.Idle;
    }

    private void ensureMinSpeed() {
        slideControl.updateSlides();
        telemetry.addData("Slide speed: ",slideControl.getSpeed());
    }

    @Override
    public void idle() {
        if (!edgeDetection.rising(outtakeTrigger.getTrigger())) return;

        outtakeState = SubsystemState.Start;
    }
    public SubsystemState getOuttakeState() {
        return outtakeState;
    }
    public OuttakeDependencies getOuttakeDependencies()
    {
        return outtakeDependencies;
    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }
}
