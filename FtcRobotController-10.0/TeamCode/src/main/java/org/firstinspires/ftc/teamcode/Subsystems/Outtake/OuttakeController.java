package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.RobotSubsystemController;

public class OuttakeController implements RobotSubsystemController {

    private final EdgeDetection edgeDetection;
    private final OuttakeTrigger outtakeTrigger = new OuttakeTrigger();
    private final SlideLogic slideLogic;
    private SubsystemState outtakeState = SubsystemState.Idle;
    private final ElapsedTime elapsedTime = new ElapsedTime();
    private double currentWait;
    private boolean wasIfCalled = false;

    public OuttakeController(EdgeDetection edgeDetection, SlideLogic slideLogic) {
        this.edgeDetection = edgeDetection;
        this.slideLogic = slideLogic;
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
        slideLogic.stepUp();
        //outtakeServoControl.checkReleaseServoPos();
        outtakeState = SubsystemState.Run;
    }

    @Override
    public void run() {
        gamepadActions();

        //needs to go after to receive position update
        //openServo();

        if (!shouldBeStopping()) return;
        initialiseStop();
    }

    private boolean shouldBeStopping() {
        return slideLogic.getSlideExtensionTarget() == 0;
    }

    private void gamepadActions() {
        //if (edgeDetection.rising(OuttakeConstants.releaseButton))


        if (edgeDetection.rising(OuttakeConstants.upButton))
            slideLogic.stepUp();

        else if (edgeDetection.rising(OuttakeConstants.downButton))
            slideLogic.stepDown();
    }

//    public void openServo() {
//        if (outtakeServoControl.isServoOpen())
//            return;
//        int motorPos = slideControl.getCurrentMotorPositionAvg();
//        if (motorPos >= OuttakeConstants.outtakeServoOpenHeight)
//            outtakeServoControl.turnServo(StateSwitch.upOn);
//    }

    public void initialiseStop() {
        outtakeState = SubsystemState.Stop;
        //outtakeServoControl.turnServo(StateSwitch.downOn);
        addWaitTime(OuttakeConstants.outtakeServoCloseWait);
    }

    @Override
    public void stop() {
        if (currentWait > elapsedTime.seconds()) return;

        if(!wasIfCalled)
          {
          slideLogic.setSlideExtensionTarget(0);
          wasIfCalled = true;
          }

        if (!slideLogic.slidesBottomReached()) return;
        wasIfCalled = false;
        outtakeState = SubsystemState.Idle;
    }

    @Override
    public void idle() {
        if (!edgeDetection.rising(outtakeTrigger.getTrigger())) return;

        outtakeState = SubsystemState.Start;
    }
    @Override
    public SubsystemState getState() {
        return outtakeState;
    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }
}
