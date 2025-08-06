package org.firstinspires.ftc.teamcode.Subsystems.Intake.SampleClaw;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class SampleClawControl {
    private final ServoControl servoControl;
    private SampleClawStates prevSampleClawState;

    public SampleClawControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevSampleClawState != IntakeStates.getSampleClawState()) {
            updateStates();
            prevSampleClawState = IntakeStates.getSampleClawState();
        }
    }

    private void updateStates() {
        switch (IntakeStates.getSampleClawState()) {
            case closed:
                servoControl.setServoPos(ServoConstants.release, IntakeConstants.releaseServoMinPos);
                break;
            case freeMove:
                servoControl.setServoPos(ServoConstants.release, IntakeConstants.releaseServoFreeMovePos);
                break;
            case halfOpen:
                servoControl.setServoPos(ServoConstants.release, IntakeConstants.releaseServoReleasePos);
                break;
            case fullyOpen:
                servoControl.setServoPos(ServoConstants.release, IntakeConstants.releaseServoMaxPos);
                break;
        }
    }
}
