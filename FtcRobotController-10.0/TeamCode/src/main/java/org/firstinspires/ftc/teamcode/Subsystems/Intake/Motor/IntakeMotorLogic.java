package org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class IntakeMotorLogic {
    private final MotorControl motorControl;
    private boolean wasOverCurrent = false;

    public IntakeMotorLogic(MotorControl motorControl) {
        this.motorControl = motorControl;
    }

    public void update() {
        boolean isOverCurrent = motorControl.isOverCurrent(MotorConstants.intake);
        if(isOverCurrent && !wasOverCurrent) {
            IntakeStates.setMotorState(IntakeMotorStates.backward);
            wasOverCurrent = true;
        }

        if(!isOverCurrent && wasOverCurrent){
            IntakeStates.setMotorState(IntakeMotorStates.forward);
            wasOverCurrent = false;
        }

    }
}
