package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides;

import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class VerticalSlideControl {
    private final SlideLogic slideLogic;
    private VerticalSlideStates prevVerticalSlideStates = VerticalSlideStates.closed;

    public VerticalSlideControl(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void update() {
        if(OuttakeStates.getVerticalSlideState() != prevVerticalSlideStates) {
            updateStates();
            prevVerticalSlideStates = OuttakeStates.getVerticalSlideState();
        }
    }

    private void updateStates() {
        switch(OuttakeStates.getVerticalSlideState()){
            case closed:
                slideLogic.setSlideExtensionTarget(0);
                break;
            case lowBasket:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.lowBasketPos);
                break;
            case highBasket:
                slideLogic.setSlideExtensionTarget(OuttakeConstants.highBasketPos);
                break;
        }
    }
}
