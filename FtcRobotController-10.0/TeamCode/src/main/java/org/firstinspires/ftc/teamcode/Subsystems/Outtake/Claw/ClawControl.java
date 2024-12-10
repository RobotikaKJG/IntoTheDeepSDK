package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Claw;

import org.firstinspires.ftc.teamcode.HardwareInterface.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class ClawControl {
    private final ServoControl servoControl;
    private ClawStates prevClawState;

    public ClawControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevClawState != OuttakeStates.getClawState()) {
            updateStates();
            prevClawState = OuttakeStates.getClawState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getClawState()) {
            case closed:
                servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMinPos);
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
