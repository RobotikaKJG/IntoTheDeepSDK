package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.DcMotor;

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
        //motorControl.setMotorSpeed(MotorConstants.extendo, power);
        motorControl.setMotorPos(MotorConstants.extendo, (int) power);
        motorControl.setMotorMode(MotorConstants.extendo, DcMotor.RunMode.RUN_TO_POSITION);

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
