package org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class IntakeMotorControl {
    private final MotorControl motorControl;
    private IntakeRotationStates prevMotorRotationStates = IntakeRotationStates.idle;

    public IntakeMotorControl(MotorControl motorControl) {
        this.motorControl = motorControl;

    }

    public void update() {
        if(IntakeStates.getMotorState() != prevMotorRotationStates) {
            updateStates();
            prevMotorRotationStates = IntakeStates.getMotorState();
        }
    }

    public void updateStates() {
        switch (IntakeStates.getMotorState()) {
            case forward:
                motorControl.setMotorSpeed(MotorConstants.intake, IntakeConstants.getIntakeSpeed());
                break;
            case backward:
                motorControl.setMotorSpeed(MotorConstants.intake, -IntakeConstants.getIntakeSpeed());
                break;
            case idleWasForward:
            case idleWasBackward:
                motorControl.setMotorSpeed(MotorConstants.intake, 0);
                break;
        }
    }
}
