package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
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
            case drop:
                drop();
                break;
        }
    }


    private void up() {
        servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoSamplePos);
        servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoSamplePos);
    }



    private void down() {
        servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoMaxPos);
        servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoMaxPos);
    }

    private void drop() {
        servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoMinPos);
        servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoMinPos);
    }
}
