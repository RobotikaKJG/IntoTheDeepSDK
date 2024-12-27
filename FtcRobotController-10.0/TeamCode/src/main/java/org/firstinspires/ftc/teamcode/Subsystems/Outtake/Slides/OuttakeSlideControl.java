package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareInterface.LimitSwitches;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideControl;

public class OuttakeSlideControl implements SlideControl {
    private final MotorControl motorControl;
    private final OuttakeSlideProperties outtakeSlideProperties = new OuttakeSlideProperties();
    private final SensorControl sensorControl;

    public OuttakeSlideControl(MotorControl motorControl, SensorControl sensorControl) {
        this.motorControl = motorControl;
        this.sensorControl = sensorControl;
    }
    @Override
    public int getSlidePosition() {
        return motorControl.getMotorPosition(MotorConstants.extendo);
    }

    @Override
    public void setSlidePosition(int position) {
        motorControl.setMotorPos(MotorConstants.bothSlides, position);
        setSlideMode(DcMotor.RunMode.RUN_TO_POSITION);
        limitSpeed(outtakeSlideProperties.getSlideMovementMaxSpeed());
        motorControl.setMotors(MotorConstants.bothSlides);
    }

    @Override
    public void setSlideMode(DcMotor.RunMode mode) {
        motorControl.setMotorMode(MotorConstants.bothSlides, mode);
    }

    @Override
    public void limitSpeed(double power) {
        motorControl.setMotorSpeed(MotorConstants.bothSlides, power);
    }

    @Override
    public void resetEncoders() {
        motorControl.resetMotorEncoders(MotorConstants.bothSlides);
    }

    @Override
    public boolean isLimitSwitchPressed() {
        return sensorControl.isLimitSwitchPressed(LimitSwitches.slide);
    }
}
