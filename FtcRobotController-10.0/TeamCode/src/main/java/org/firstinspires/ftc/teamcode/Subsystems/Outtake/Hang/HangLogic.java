package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Hang;

import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class HangLogic {

    private final SlideLogic slideLogic;

    public HangLogic(SlideLogic slideLogic) {
        this.slideLogic = slideLogic;
    }

    public void update(){
        switch (OuttakeStates.getHangState()) {
            case extendSlides:
                extendSlides();
                break;
            case retractSlides:
                retractSlides();
                break;
            case hangOnHooks:
                hangOnHooks();
                break;
        }
    }

    private void extendSlides() {
//        if(!slidesAtTarget()) return;

        OuttakeStates.setHangState(HangStates.waitForButton);
    }

    private void retractSlides() {
        if(!slidesAtTarget()) return;

//        OuttakeStates.setHangState(HangStates.hangOnHooks);

    }

    private void hangOnHooks() {
        OuttakeStates.setHangState(HangStates.idle);
    }


    private boolean slidesAtTarget() {
        return Math.abs(slideLogic.getSlidePosition() - slideLogic.getSlideExtensionTarget()) < OuttakeConstants.hangThreshold;
    }
}
