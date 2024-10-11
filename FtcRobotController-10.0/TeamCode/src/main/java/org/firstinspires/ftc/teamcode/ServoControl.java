package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoControl {
    private final HardwareMap hardwareMap;
    Servo servo;

    public ServoControl(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        servo = hardwareMap.servo.get("servo");
        setServoPos(0);
    }

    public void setServoPos(double position) {
        servo.setPosition(position);
    }
}
