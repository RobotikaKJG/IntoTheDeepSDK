package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.Enums.StateSwitch;

public class IntakeMotorControl {
    private final MotorControl motorControl;

    public IntakeMotorControl(MotorControl motorControl) {
        this.motorControl = motorControl;
    }

    public void motor(StateSwitch stateSwitch) {
        motor(stateSwitch,IntakeConstants.intakeSpeed);
    }

    public void motor(StateSwitch stateSwitch, double speed) {
        switch (stateSwitch) {
            case downOn:
                motorControl.setMotorSpeed(MotorConstants.intake, -speed);
                break;

            case upOn:
                motorControl.setMotorSpeed(MotorConstants.intake, speed);
                break;

            case noOn:
                motorControl.setMotorSpeed(MotorConstants.intake, 0.0);
                break;
        }
    }
}
