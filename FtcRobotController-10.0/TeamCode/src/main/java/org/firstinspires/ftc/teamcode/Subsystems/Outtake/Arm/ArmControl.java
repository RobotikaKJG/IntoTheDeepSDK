package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm;

import org.firstinspires.ftc.teamcode.HardwareInterface.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class ArmControl {
    private final ServoControl servoControl;
    private ArmStates prevArmState;

    public ArmControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevArmState != OuttakeStates.getArmState()) {
            updateStates();
            prevArmState = OuttakeStates.getArmState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getArmState()) {
            case up:
                up();
                break;
            case down:
                down();
                break;
        }
    }

    private void up() {
        servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoMinPos);
        servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoMinPos);
    }

    private void down() {
        servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoMaxPos);
        servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoMaxPos);
    }
}
