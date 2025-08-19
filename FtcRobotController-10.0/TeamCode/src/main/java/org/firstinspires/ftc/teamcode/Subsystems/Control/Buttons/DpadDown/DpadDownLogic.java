package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadDown;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class DpadDownLogic {
    private final DpadDownControl dpadDownControl = new DpadDownControl();

    public void update() {
        if (outtakeIdle()) return;
    }

    private void completeAction(){
        dpadDownControl.update();
        ButtonStates.setDpadDownState(DpadDownStates.idle);
    }

    private boolean outtakeIdle() {
        if(IntakeStates.getArmSlideState() == ArmSlideStates.extended) return false;
        ButtonStates.setDpadDownState(DpadDownStates.down);
        completeAction();
        return true;
    }
}
