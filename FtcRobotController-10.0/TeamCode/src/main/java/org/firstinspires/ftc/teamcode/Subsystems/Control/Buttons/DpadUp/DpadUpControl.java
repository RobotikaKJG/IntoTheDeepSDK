package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadUp;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;

public class DpadUpControl {

    public void update() {
        switch (ButtonStates.getDpadUpState()) {
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
                IntakeStates.setMotorState(IntakeMotorStates.backward);
                break;
            case backward:
                IntakeStates.setMotorState(IntakeMotorStates.forward);
                break;
            case idleWasForward:
                IntakeStates.setMotorState(IntakeMotorStates.idleWasBackward);
                break;
            case idleWasBackward:
                IntakeStates.setMotorState(IntakeMotorStates.idleWasForward);
                break;
        }
    }
}
