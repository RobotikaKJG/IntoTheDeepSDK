package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftTrigger;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.CloseActions.AutoClose.AutoCloseStates;
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
        switch(IntakeStates.getMotorState()){
            case forward:
                IntakeStates.setMotorState(IntakeMotorStates.idleWasForward);
//                IntakeStates.setAutoCloseStates(AutoCloseStates.checkColor); // REMOVE LATER, NOTE
                break;
            case backward:
                IntakeStates.setMotorState(IntakeMotorStates.idleWasBackward);
                break;
            case idleWasForward:
                IntakeStates.setMotorState(IntakeMotorStates.forward);
//                IntakeStates.setAutoCloseStates(AutoCloseStates.checkColor); // REMOVE LATER, NOTE
                break;
            case idleWasBackward:
                IntakeStates.setMotorState(IntakeMotorStates.backward);
                break;
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
