package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadLeft;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Hang.HangStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

public class DpadLeftLogic {
    private final DpadLeftControl dpadLeftControl = new DpadLeftControl();

    public void update() {
        if(hangOn()) return;
        if(hangOff()) return;

    }

    private boolean hangOn() {
        if((IntakeStates.getHangState() == HangStates.pivotUp || IntakeStates.getHangState() == HangStates.extendSlides || IntakeStates.getHangState() == HangStates.waitForButton) || IntakeStates.getArmSlideState() == ArmSlideStates.extended) return false;
        ButtonStates.setDpadLeftState(DpadLeftStates.hangOn);
        completeAction();
        return true;
    }

    private boolean hangOff() {
        if(!(IntakeStates.getHangState() == HangStates.pivotUp || IntakeStates.getHangState() == HangStates.extendSlides || IntakeStates.getHangState() == HangStates.waitForButton)) return false;
        ButtonStates.setDpadLeftState(DpadLeftStates.hangOff);
        completeAction();
        return true;
    }

    private void completeAction(){
        dpadLeftControl.update();
        ButtonStates.setDpadLeftState(DpadLeftStates.idle);
    }
}
