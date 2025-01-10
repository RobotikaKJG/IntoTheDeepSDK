package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.LimitSwitches;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Slide.SlideControl;

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
        return motorControl.getMotorPosition(MotorConstants.bothSlides);
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
        //return sensorControl.isLimitSwitchPressed(LimitSwitches.slide);
        if(getSlidePosition() < 10)
        {
            motorControl.setMotorSpeed(MotorConstants.extendo,0);
            motorControl.setMotors(MotorConstants.extendo);
            return true;
        }
        return false;
    }
}
