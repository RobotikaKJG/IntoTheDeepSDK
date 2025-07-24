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
            case aBitUp:
                aBitUp();
                break;
            case down:
                down();
                break;
        }
    }

    private void aBitUp() {
        servoControl.setServoPos(ServoConstants.outtake, OuttakeConstants.outtakeServoOverIntakePos);

    }


    private void up() {
        servoControl.setServoPos(ServoConstants.outtake, OuttakeConstants.outtakeServoMaxPos);
    }



    private void down() {
        servoControl.setServoPos(ServoConstants.outtake, OuttakeConstants.outtakeServoMinPos);
    }


}
