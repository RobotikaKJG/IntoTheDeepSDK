package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideControl;

public class IntakeSlideControl implements SlideControl {
    private final MotorControl motorControl;

    public IntakeSlideControl(MotorControl motorControl) {
        this.motorControl = motorControl;
    }
    @Override
    public int getSlidePosition() {
        return motorControl.getMotorPosition(MotorConstants.extendo);
    }

    @Override
    public void setSlides(double power) {
        motorControl.setMotorSpeed(MotorConstants.extendo, power);
    }

    @Override
    public void limitSpeed(double power) {
        motorControl.limitSpeed(MotorConstants.extendo, power);
    }

    @Override
    public void resetEncoders() {
        motorControl.resetMotorEncoders(MotorConstants.extendo);
    }
}
