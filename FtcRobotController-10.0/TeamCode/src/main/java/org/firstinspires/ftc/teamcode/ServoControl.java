package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoControl {
    private final HardwareMap hardwareMap;
    private Servo servo;
    private CRServo crservo;

    // Constructor for controlling a single Servo or CRServo
    public ServoControl(HardwareMap hardwareMap, String servoName, boolean isContinuous) {
        this.hardwareMap = hardwareMap;

        if (isContinuous) {
            crservo = hardwareMap.crservo.get(servoName);
        } else {
            servo = hardwareMap.servo.get(servoName);
        }
    }

    // Set position for standard servo
    public void setServoPos(double position) {
        if (servo != null) {
            servo.setPosition(position);
        } else {
            throw new IllegalStateException("This ServoControl is not configured for a standard servo.");
        }
    }

    // Set speed for continuous servo
    public void setServoSpeed(double speed) {
        if (crservo != null) {
            crservo.setPower(speed);
        } else {
            throw new IllegalStateException("This ServoControl is not configured for a continuous servo.");
        }
    }
}