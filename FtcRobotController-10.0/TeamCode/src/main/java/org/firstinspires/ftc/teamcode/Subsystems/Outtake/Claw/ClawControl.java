package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class ClawControl {
    private final ServoControl servoControl;
    private ClawStates prevSampleClawState;

    public ClawControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevSampleClawState != OuttakeStates.getClawState()) {
            updateStates();
            prevSampleClawState = OuttakeStates.getClawState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getClawState()) {
            case closed:
                servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMinPos);
                break;
//            case freeMove:
//                servoControl.setServoPos(ServoConstants.release,OuttakeConstants.releaseServoFreeMovePos);
//                break;
//            case halfOpen:
//                servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoReleasePos);
//                break;
            case fullyOpen:
                servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMaxPos);
                break;
        }
    }
}
