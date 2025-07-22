package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftTrigger;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot.PivotStates;
import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class LeftTriggerControl {

    public LeftTriggerControl() {
    }

    public void update() {
        switch(ButtonStates.getLeftTriggerState()){
            case toggleIntakeMotor:
                toggleIntakeMotor();
                break;
            case moveSlidesDown:
                moveSlidesDown();
                break;
            case idle:
                break;
        }
    }

    private void toggleIntakeMotor() {
        if(IntakeStates.getMotorState() == IntakeMotorStates.forward)
            IntakeStates.setMotorState(IntakeMotorStates.idleWasForward);
        else {
            IntakeStates.setMotorState(IntakeMotorStates.forward);
            IntakeStates.setPivotState(PivotStates.down);
        }
    }
    private void moveSlidesDown() {
        switch(OuttakeStates.getVerticalSlideState()){
            case lowBasket:
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.close);
                break;
            case highBasket:
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.lowBasket);
        }
    }
}
