package org.firstinspires.ftc.teamcode.Subsystems.Outtake;

import org.firstinspires.ftc.teamcode.HardwareInterface.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.SlideControl;

public class OuttakeDependencies {
    public SlideControl slideControl;
    public OuttakeServoControl outtakeServoControl;

    public OuttakeDependencies(MotorControl motorControl, ServoControl servoControl, SensorControl sensorControl) {
        slideControl = new SlideControl(motorControl, sensorControl);
        outtakeServoControl = new OuttakeServoControl(servoControl);
    }
}
