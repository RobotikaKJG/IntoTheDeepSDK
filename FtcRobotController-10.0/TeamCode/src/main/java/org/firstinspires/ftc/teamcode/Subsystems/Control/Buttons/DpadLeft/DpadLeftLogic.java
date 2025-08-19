package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadLeft;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

public class DpadLeftLogic {
    private final DpadLeftControl dpadLeftControl = new DpadLeftControl();

    public void update() {

    }

    private void completeAction(){
        dpadLeftControl.update();
        ButtonStates.setDpadLeftState(DpadLeftStates.idle);
    }
}
