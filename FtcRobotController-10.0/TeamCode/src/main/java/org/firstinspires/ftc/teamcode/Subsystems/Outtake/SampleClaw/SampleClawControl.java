package org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class SampleClawControl {
    private final ServoControl servoControl;
    private SampleClawStates prevSampleClawState;

    public SampleClawControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevSampleClawState != OuttakeStates.getSampleClawState()) {
            updateStates();
            prevSampleClawState = OuttakeStates.getSampleClawState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getSampleClawState()) {
            case closed:
                servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMinPos);
                break;
            case freeMove:
                servoControl.setServoPos(ServoConstants.release,OuttakeConstants.releaseServoFreeMovePos);
                break;
            case halfOpen:
                servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoReleasePos);
                break;
            case fullyOpen:
                servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMaxPos);
                break;
        }
    }
}
