package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Pivot;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class OuttakePivotControl {
    private final ServoControl servoControl;
    private OuttakePivotStates prevPivotState;

    public OuttakePivotControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevPivotState != OuttakeStates.getPivotState()) {
            updateStates();
            prevPivotState = OuttakeStates.getPivotState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getPivotState()) {
            case up:
                up();
                break;
            case down:
                down();
                break;
        }
    }


    private void up() {
        servoControl.setServoPos(ServoConstants.outtakePivot, OuttakeConstants.outtakeRightServoSamplePos);
    }



    private void down() {
        servoControl.setServoPos(ServoConstants.outtakePivot, OuttakeConstants.outtakePivotServoMaxPos);
    }


}
