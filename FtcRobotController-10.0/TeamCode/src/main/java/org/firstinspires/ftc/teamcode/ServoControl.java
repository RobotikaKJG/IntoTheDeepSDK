package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoControl {
    private final HardwareMap hardwareMap;
    Servo servo;
    CRServo crservo;

    public ServoControl(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        servo = hardwareMap.servo.get("servo");
        crservo = hardwareMap.crservo.get("intakeServo");
        setServoPos(0);
    }

    public void setServoPos(double position) {
        servo.setPosition(position);
    }

    public void setServoSpeed(double speed)
    {
        crservo.setPower(speed);
    }
}
