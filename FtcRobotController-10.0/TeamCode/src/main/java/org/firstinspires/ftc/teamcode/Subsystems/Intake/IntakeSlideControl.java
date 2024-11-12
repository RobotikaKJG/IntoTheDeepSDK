package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareInterface.LimitSwitches;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideControl;

public class IntakeSlideControl implements SlideControl {
    private final MotorControl motorControl;
    private final IntakeSlideProperties intakeSlideProperties = new IntakeSlideProperties();
    private final SensorControl sensorControl;

    public IntakeSlideControl(MotorControl motorControl, SensorControl sensorControl) {
        this.motorControl = motorControl;
        this.sensorControl = sensorControl;
    }
    @Override
    public int getSlidePosition() {
        return motorControl.getMotorPosition(MotorConstants.extendo);
    }

    @Override
    public void setSlidePosition(int position) {
        motorControl.setMotorPos(MotorConstants.extendo, position);
        setSlideMode(DcMotor.RunMode.RUN_TO_POSITION);
        limitSpeed(intakeSlideProperties.getSlideMovementMaxSpeed());
        motorControl.setMotors(MotorConstants.extendo);
    }

    @Override
    public void setSlideMode(DcMotor.RunMode mode) {
        motorControl.setMotorMode(MotorConstants.extendo, mode);
    }

    @Override
    public void limitSpeed(double power) {
        motorControl.setMotorSpeed(MotorConstants.extendo, power);
    }

    @Override
    public void resetEncoders() {
        motorControl.resetMotorEncoders(MotorConstants.extendo);
    }

    @Override
    public boolean isLimitSwitchPressed() {
        return sensorControl.isLimitSwitchPressed(LimitSwitches.extendo);
    }
}
