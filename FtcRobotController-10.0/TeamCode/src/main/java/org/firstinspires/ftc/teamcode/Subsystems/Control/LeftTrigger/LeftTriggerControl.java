package org.firstinspires.ftc.teamcode.Subsystems.Control.LeftTrigger;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeRotationStates;
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
        IntakeStates.setMotorState(IntakeRotationStates.forward);
        IntakeStates.setServoState(IntakeRotationStates.forward);
        IntakeStates.setIntakeState(SubsystemState.Run);
    }

    private void toggleIntakeMotor() {
        switch(IntakeStates.getMotorState()){
            case forward:
                IntakeStates.setMotorState(IntakeRotationStates.idleWasForward);
                IntakeStates.setServoState(IntakeRotationStates.idleWasForward);
                break;
            case backward:
                IntakeStates.setMotorState(IntakeRotationStates.idleWasBackward);
                IntakeStates.setServoState(IntakeRotationStates.idleWasBackward);
                break;
            case idleWasForward:
                IntakeStates.setMotorState(IntakeRotationStates.forward);
                IntakeStates.setServoState(IntakeRotationStates.forward);
                break;
            case idleWasBackward:
                IntakeStates.setMotorState(IntakeRotationStates.backward);
                IntakeStates.setServoState(IntakeRotationStates.backward);
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
