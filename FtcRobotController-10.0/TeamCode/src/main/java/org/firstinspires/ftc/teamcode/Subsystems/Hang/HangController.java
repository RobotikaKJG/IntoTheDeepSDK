package org.firstinspires.ftc.teamcode.Subsystems.Hang;

import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.RobotSubsystemController;

public class HangController implements RobotSubsystemController {

    private final HangTrigger hangTrigger = new HangTrigger();
    private final EdgeDetection edgeDetection;
    private final SlideLogic slideControl;
    private SubsystemState hangState = SubsystemState.Idle;

    public HangController(SlideLogic slideControl, EdgeDetection edgeDetection) {
        this.edgeDetection = edgeDetection;
        this.slideControl = slideControl;
    }

    @Override
    public void updateState() {
        switch (hangState) {
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
        //slideControl.updateSlides();

        if (!edgeDetection.rising(hangTrigger.getTrigger())) return;
        slideControl.setSlideExtensionTarget(HangConstants.hangHeight);
        hangState = SubsystemState.Run;
    }

    @Override
    public void run() {
        //slideControl.updateSlides();
        if (!edgeDetection.rising(hangTrigger.getTrigger())) return;
        hangState = SubsystemState.Idle;
        //slideControl.setSlideExtensionTarget(HangConstants.hangStartHeight);
    }

    @Override
    public void stop() {
        //slideControl.updateSlides();
        if (!edgeDetection.rising(hangTrigger.getTrigger())) return;
        hangState = SubsystemState.Idle;
    }

    @Override
    public void idle() {
        if (!edgeDetection.rising(hangTrigger.getTrigger())) return;
        hangState = SubsystemState.Start;
        slideControl.setSlideExtensionTarget(HangConstants.hangStartHeight);
    }

    @Override
    public SubsystemState getState() {
        return hangState;
    }
}
