package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class ArmControl {
    private ArmStates prevArmState;
    private ServoControl servoControl;

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
            case takeSpecimen:
                servoControl.setServoPos(ServoConstants.outtakeArm, OuttakeConstants.outtakeArmServoTakePos);
                break;
            case placeSpecimen:
                servoControl.setServoPos(ServoConstants.outtakeArm, OuttakeConstants.outtakeArmServoPlacePos);
                break;
            case releaseSpecimen:
                servoControl.setServoPos(ServoConstants.outtakeArm, OuttakeConstants.outtakeArmServoReleasePos);
                break;
            case maxPos:
                servoControl.setServoPos(ServoConstants.outtakeArm, OuttakeConstants.outtakeArmServoMaxPos);
                break;
            case down:
                servoControl.setServoPos(ServoConstants.outtakeArm, OuttakeConstants.outtakeArmServoIdlePos);
                break;
            case idle:
                break;
        }
    }
}
