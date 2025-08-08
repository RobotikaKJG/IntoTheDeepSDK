package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftTrigger;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch.LatchStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides.ArmSlideStates;

public class LeftTriggerControl {
    public void update() {
        switch (ButtonStates.getLeftTriggerState()) {
            case takeSample:
                takeSample();
                break;
            case sampleTaken:
                break;
            case idle:
                break;
        }
    }

    private void takeSample() {
        IntakeStates.setMotorState(IntakeMotorStates.forward);
        IntakeStates.setPivotState(PivotStates.down);
    }
}
