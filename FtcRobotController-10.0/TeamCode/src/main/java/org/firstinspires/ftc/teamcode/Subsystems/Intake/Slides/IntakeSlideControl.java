package org.firstinspires.ftc.teamcode.Subsystems.Intake.Slides;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.LimitSwitches;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;

public class IntakeSlideControl implements SlideControl {
    private final MotorControl motorControl;
    private final IntakeSlideProperties intakeSlideProperties = new IntakeSlideProperties();
    private final SensorControl sensorControl;
    private int targetPosition = 0;
    public static int currentPosition = 0; //me don't like this a lot, NOTE
    public double maxSpeed = 0;

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
        //System.out.println(currentPosition);
        return currentPosition;
    }

    @Override
    public void setSlidePosition(int position) {
        targetPosition = position;
        motorControl.setMotorPos(MotorConstants.bothSlides, position);
        setSlideMode(DcMotor.RunMode.RUN_TO_POSITION);
        limitSpeed(intakeSlideProperties.getSlideMovementMaxSpeed());
    }

    @Override
    public void setSlideMode(DcMotor.RunMode mode) {
        motorControl.setMotorMode(MotorConstants.bothSlides, mode);
    }

    @Override
    public void limitSpeed(double power) {
        maxSpeed = power;
        //System.out.println(power);
        motorControl.setMotorSpeed(MotorConstants.bothSlides, power);
        motorControl.setMotors(MotorConstants.bothSlides);
    }

    @Override
    public void resetEncoders() {
        motorControl.resetMotorEncoders(MotorConstants.bothSlides);
    }

    @Override
    public boolean isLimitSwitchPressed() {
        return retractSlide(MotorConstants.bothSlides, LimitSwitches.slides);
    }

    private boolean retractSlide(int slide, LimitSwitches limitSwitch) {
        if(sensorControl.isLimitSwitchPressed(limitSwitch)){
            motorControl.setMotorMode(slide, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorControl.setMotorSpeed(slide, 0);
            motorControl.setMotors(slide);
            return true;
        }

        if(currentPosition > IntakeConstants.limitSwitchThreshold)
            return false;

        targetPosition -= IntakeConstants.limitSwitchRetractionStep;
        limitSpeed(intakeSlideProperties.getSlideMovementMaxSpeed());
        motorControl.setMotorPos(slide, targetPosition);
        return false;
    }
}
