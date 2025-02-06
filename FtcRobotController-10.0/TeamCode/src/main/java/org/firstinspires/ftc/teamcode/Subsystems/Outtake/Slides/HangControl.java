package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;

public class HangControl {
    private final SlideLogic slideLogic;
    private boolean isHanging = false;

    public HangControl(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void toggleHang() {
        if (isHanging) {
            // Retract the slides to the closed position
            OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
            slideLogic.setSlideExtensionTarget(20);
        } else {
            // Extend the slides to the high basket position
            OuttakeStates.setVerticalSlideState(VerticalSlideStates.highBasket);
            slideLogic.setSlideExtensionTarget(OuttakeConstants.highBasketPos);
        }
        isHanging = !isHanging; // Toggle hanging state
    }

    public void update() {
        // Update the state transitions in VerticalSlideControl
        if (slideLogic.slidesBottomReached() && OuttakeStates.getVerticalSlideState() == VerticalSlideStates.closing) {
            OuttakeStates.setVerticalSlideState(VerticalSlideStates.closed);
        }
    }

    // Getter for isHanging
    public boolean isHanging() {
        return isHanging;
    }
}
