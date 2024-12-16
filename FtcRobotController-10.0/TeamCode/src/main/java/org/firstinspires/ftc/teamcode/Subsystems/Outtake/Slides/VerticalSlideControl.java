package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides;

import org.firstinspires.ftc.teamcode.HardwareInterface.SlideLogic;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw.ClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.ReleaseButtonActions.ReleaseButtonStates;

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
                OuttakeStates.setArmState(ArmStates.down);
                OuttakeStates.setReleaseButtonState(ReleaseButtonStates.idle);
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
