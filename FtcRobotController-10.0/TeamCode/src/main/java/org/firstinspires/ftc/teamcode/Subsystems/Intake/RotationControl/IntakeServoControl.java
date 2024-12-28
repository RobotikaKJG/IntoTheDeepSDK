package org.firstinspires.ftc.teamcode.Subsystems.Intake.RotationControl;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class IntakeServoControl {
    private final ServoControl servoControl;
    private IntakeRotationStates prevServoState = IntakeStates.getServoState();

    public IntakeServoControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (IntakeStates.getServoState() != prevServoState){
            setState();
            prevServoState = IntakeStates.getServoState();
        }
    }

    private void setState() {
        switch (IntakeStates.getServoState()) {
            case forward:
                servoControl.setServoSpeed(0, IntakeConstants.servoSpeed);
                break;
            case backward:
                servoControl.setServoSpeed(0, -IntakeConstants.servoSpeed);
                break;
            case idleWasForward:
            case idleWasBackward:
            case idle:
                servoControl.setServoSpeed(0, 0);
        }
    }
}
