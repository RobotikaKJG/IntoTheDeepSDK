package org.firstinspires.ftc.teamcode.Subsystems.Control.DpadUp;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl.IntakeRotationStates;

public class DpadUpControl {

    public void update() {
        switch (ControlStates.getDpadUpState()) {
            case toggleMotor:
                toggleMotor();
                break;
            case idle:
                break;
        }
    }

    private void toggleMotor() {
        switch(IntakeStates.getMotorState()){
            case forward:
                IntakeStates.setMotorState(IntakeRotationStates.backward);
                break;
            case backward:
                IntakeStates.setMotorState(IntakeRotationStates.forward);
                break;
            case idleWasForward:
                IntakeStates.setMotorState(IntakeRotationStates.idleWasBackward);
                break;
            case idleWasBackward:
                IntakeStates.setMotorState(IntakeRotationStates.idleWasForward);
                break;
        }
    }
}
