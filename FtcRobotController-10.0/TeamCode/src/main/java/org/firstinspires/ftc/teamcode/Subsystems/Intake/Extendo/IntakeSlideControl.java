package org.firstinspires.ftc.teamcode.Subsystems.Intake.Extendo;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.LimitSwitches;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideControl;

public class IntakeSlideControl implements SlideControl {
    private final MotorControl motorControl;
    private final IntakeSlideProperties intakeSlideProperties = new IntakeSlideProperties();
    private final SensorControl sensorControl; //keep this for when limit switches exist
    private int targetPosition = 0;
    private int currentPosition = 0;

    public IntakeSlideControl(MotorControl motorControl, SensorControl sensorControl) {
        this.motorControl = motorControl;
        this.sensorControl = sensorControl;
    }
    @Override
    public void updateSlidePosition() {
        currentPosition = motorControl.getMotorPosition(MotorConstants.bothSlides);
    }

    @Override
    public int getSlidePosition() {
        return currentPosition;
    }
    @Override
    public void setSlidePosition(int position) {
        targetPosition = position;
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
        return retractSlide();
    }

    private boolean retractSlide() {
        if(sensorControl.isLimitSwitchPressed(LimitSwitches.extendo) || motorControl.isOverCurrent(MotorConstants.extendo)) {
            motorControl.setMotorMode(MotorConstants.extendo, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorControl.setMotorMode(MotorConstants.extendo, DcMotor.RunMode.RUN_TO_POSITION);
            motorControl.setMotorSpeed(MotorConstants.extendo, 0);//0.1);
            motorControl.setMotors(MotorConstants.extendo);
            return true;
        }

        if(motorControl.getMotorPosition(MotorConstants.extendo) > 25)
            return false;

        targetPosition -= 15;
        motorControl.setMotorPos(MotorConstants.extendo, targetPosition);
        return false;
    }
}
