package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.LeftTrigger;

import org.firstinspires.ftc.teamcode.Subsystems.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class LeftTriggerControl {

    public LeftTriggerControl() {
    }

    public void update() {
        switch(ControlStates.getLeftTriggerState()){
            case activateIntakeMotor:
                activateIntakeMotor();
                break;
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

    private void activateIntakeMotor() {
        IntakeStates.setMotorState(IntakeMotorStates.forward);
        IntakeStates.setIntakeState(SubsystemState.Run);
    }

    private void toggleIntakeMotor() {
        switch(IntakeStates.getMotorState()){
            case forward:
                IntakeStates.setMotorState(IntakeMotorStates.idleWasForward);
                break;
            case backward:
                IntakeStates.setMotorState(IntakeMotorStates.idleWasBackward);
                break;
            case idleWasForward:
                IntakeStates.setMotorState(IntakeMotorStates.forward);
                break;
            case idleWasBackward:
                IntakeStates.setMotorState(IntakeMotorStates.backward);
                break;
        }
    }
    private void moveSlidesDown() {
        switch(OuttakeStates.getVerticalSlideState()){
            case lowBasket:
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.closed);
                break;
            case highBasket:
                OuttakeStates.setVerticalSlideState(VerticalSlideStates.lowBasket);
        }
    }
}
