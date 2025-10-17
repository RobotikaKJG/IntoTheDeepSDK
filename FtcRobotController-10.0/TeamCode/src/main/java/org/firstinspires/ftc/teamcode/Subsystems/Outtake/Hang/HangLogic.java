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
            case releasePTO:
                releasePTO();
                break;
            case lockPTO:
                lockPTO();
                break;
            case extendSlides:
                extendSlides();
                break;
            case retractSlides:
                retractSlides();
                break;
            case hangOnHooks:
                hangOnHooks();
                break;
            case retracted:
                break;
            case idle:
                break;
        }
    }

    private void releasePTO() {
    }

    private void lockPTO() {
    }

    private void extendSlides() {
//        if(!slidesAtTarget()) return;

    }

    private void retractSlides() {
//        if(!slidesAtTarget()) return;
//
//        OuttakeStates.setHangState(HangStates.hangOnHooks);

    }

    private void hangOnHooks() {
    }


    private boolean slidesAtTarget() {
        return Math.abs(slideLogic.getSlidePosition() - slideLogic.getSlideExtensionTarget()) < OuttakeConstants.hangThreshold;
    }
}
