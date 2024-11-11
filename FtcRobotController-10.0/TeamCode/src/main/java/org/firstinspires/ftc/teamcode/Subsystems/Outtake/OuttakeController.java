package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.RobotSubsystemController;

public class OuttakeController implements RobotSubsystemController {

    private final EdgeDetection edgeDetection;
    private final OuttakeTrigger outtakeTrigger = new OuttakeTrigger();
    private final SlideLogic slideLogic;
    private SubsystemState outtakeState = SubsystemState.Idle;
    private final ElapsedTime elapsedTime = new ElapsedTime();
    private final OuttakeServoController outtakeServoController;
    private double currentWait;
    private CloseButtonStates closeButtonStates = CloseButtonStates.flipArm;
    private OuttakeCloseStates outtakeCloseStates = OuttakeCloseStates.retractArm;
    private SlideStates slideStates = SlideStates.closed;

    public OuttakeController(EdgeDetection edgeDetection, SlideLogic slideLogic, OuttakeServoController outtakeServoController) {
        this.edgeDetection = edgeDetection;
        this.slideLogic = slideLogic;
        this.outtakeServoController = outtakeServoController;
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
        slideLogic.setSlideExtensionTarget(OuttakeConstants.lowBasketPos);
        slideStates = SlideStates.lowBasket;
        closeButtonStates = CloseButtonStates.flipArm;
        outtakeState = SubsystemState.Run;
    }

    @Override
    public void run() {
        gamepadActions();

        if (!shouldBeStopping()) return;
        initialiseStop();
    }

    private boolean shouldBeStopping() {
        return slideLogic.getSlideExtensionTarget() == 0;
    }

    private void gamepadActions() {

        if (edgeDetection.rising(OuttakeConstants.releaseButton))
            closeButtonActions();


        if (edgeDetection.rising(OuttakeConstants.upButton))
            stepUp();

        else if (edgeDetection.rising(OuttakeConstants.downButton))
            stepDown();
    }

    public void stepUp()
    {
        switch (slideStates)
        {
            case closed:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.lowBasketPos);
                slideStates = SlideStates.lowBasket;
                break;
            case lowBasket:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.highBasketPos);
                slideStates = SlideStates.highBasket;
                break;
            case highBasket:
                break;
        }
    }

    private void stepDown()
    {
        switch (slideStates)
        {
            case highBasket:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.lowBasketPos);
                slideStates = SlideStates.lowBasket;
                break;
            case lowBasket:
                slideLogic.setSlideExtensionTarget(0);
                slideStates = SlideStates.closed;
                initialiseStop();
                break;
            case closed:
                break;
        }
    }

    public void closeButtonActions()
    {
        switch(closeButtonStates)
        {
            case flipArm:
                outtakeServoController.setServoState(OuttakeServoStates.upClose);
                closeButtonStates = CloseButtonStates.release;
                addWaitTime(OuttakeConstants.outtakeServoWait);
                break;
            case release:
                if (currentWait > elapsedTime.seconds()) return;
                outtakeServoController.setServoState(OuttakeServoStates.upOpen);
                closeButtonStates = CloseButtonStates.retract;
                break;
            case retract:
                initialiseStop();
                break;
        }
    }

    public void initialiseStop() {
        outtakeState = SubsystemState.Stop;
        if(closeButtonStates == CloseButtonStates.retract) {
            outtakeServoController.setServoState(OuttakeServoStates.upClose);
            outtakeCloseStates = OuttakeCloseStates.retractArm;
        }
        else
            outtakeCloseStates = OuttakeCloseStates.close;
        addWaitTime(OuttakeConstants.releaseServoCloseWait);
    }

    @Override
    public void stop() {
        if (currentWait > elapsedTime.seconds()) return;

        switch (outtakeCloseStates) {
            case retractArm:
                outtakeServoController.setServoState(OuttakeServoStates.downClose);
                addWaitTime(OuttakeConstants.outtakeServoWait);
                outtakeCloseStates = OuttakeCloseStates.close;
                break;
            case close:
                if (currentWait > elapsedTime.seconds()) return;
                outtakeServoController.setServoState(OuttakeServoStates.downOpen);
                slideLogic.setSlideExtensionTarget(0);
                outtakeCloseStates = OuttakeCloseStates.retractSlides;

                break;
            case retractSlides:
                if (slideLogic.getSlidePosition() > 10) return;

                outtakeState = SubsystemState.Idle;
                break;
        }
    }

    @Override
    public void idle() {
        if (!edgeDetection.rising(outtakeTrigger.getTrigger())) return;

        outtakeState = SubsystemState.Start;
        //addWaitTime();
    }
    @Override
    public SubsystemState getState() {
        return outtakeState;
    }

    private void addWaitTime(double waitTime) {
        currentWait = elapsedTime.seconds() + waitTime;
    }
}
