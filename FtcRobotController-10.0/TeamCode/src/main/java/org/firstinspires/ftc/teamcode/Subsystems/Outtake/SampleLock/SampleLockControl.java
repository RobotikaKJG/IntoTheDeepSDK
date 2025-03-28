package org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class SampleLockControl {
    private final ServoControl servoControl;
    private SampleLockStates prevSampleLockState;

    public SampleLockControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevSampleLockState != OuttakeStates.getSampleLockState()) {
            updateStates();
            prevSampleLockState = OuttakeStates.getSampleLockState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getSampleLockState()) {
            case closed:
                servoControl.setServoPos(ServoConstants.lock, IntakeConstants.lockServoMinPos);
                break;
            case open:
                servoControl.setServoPos(ServoConstants.lock, IntakeConstants.lockServoMaxPos);
                break;
        }
    }
}
