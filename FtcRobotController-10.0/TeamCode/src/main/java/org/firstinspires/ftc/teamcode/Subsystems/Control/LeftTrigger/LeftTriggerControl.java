package org.firstinspires.ftc.teamcode.Subsystems.Control.LeftTrigger;

import org.firstinspires.ftc.teamcode.Enums.SubsystemState;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class LeftTriggerControl {

    public LeftTriggerControl() {
    }


    public void update() {
        activateIntakeMotor();
        toggleIntakeMotor();
        moveSlidesDown();
    }
    private void activateIntakeMotor() {
        IntakeStates.setIntakeState(SubsystemState.Run);
        IntakeStates.setMotorState(IntakeMotorStates.forward);
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

    }
}
