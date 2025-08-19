package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadDown;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm.ArmStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class DpadDownControl {
    public void update() {
        switch (ButtonStates.getDpadDownState()) {
            case down:
                IntakeStates.setArmSlideState(ArmSlideStates.stepDown);
                break;
            case idle:
                break;
        }
    }
}
