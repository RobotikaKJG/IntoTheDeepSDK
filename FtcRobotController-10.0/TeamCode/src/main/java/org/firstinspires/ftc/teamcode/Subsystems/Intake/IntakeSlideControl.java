package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideControl;

public class IntakeSlideControl implements SlideControl {
    private final MotorControl motorControl;
    private final IntakeSlideProperties intakeSlideProperties = new IntakeSlideProperties();

    public IntakeSlideControl(MotorControl motorControl) {
        this.motorControl = motorControl;
    }
    @Override
    public int getSlidePosition() {
        return motorControl.getMotorPosition(MotorConstants.extendo);
    }

    @Override
    public void setSlidePosition(int position) {
        motorControl.setMotorPos(MotorConstants.extendo, position);
        motorControl.setMotorMode(MotorConstants.extendo, DcMotor.RunMode.RUN_TO_POSITION);
        limitSpeed(intakeSlideProperties.getSlideMovementMaxSpeed());
        motorControl.setMotors(MotorConstants.extendo);
    }

    @Override
    public void limitSpeed(double power) {
        motorControl.setMotorSpeed(MotorConstants.extendo, power);
    }

    @Override
    public void resetEncoders() {
        motorControl.resetMotorEncoders(MotorConstants.extendo);
    }
}
