package org.firstinspires.ftc.teamcode.Subsystems.Outtake.PTO;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class PTOControl {
    private final ServoControl servoControl;
    private PTOStates prevPTOState;

    public PTOControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevPTOState != OuttakeStates.getPtoState()) {
            updateStates();
            prevPTOState = OuttakeStates.getPtoState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getPtoState()) {
            case closed:
                closed();
                break;
            case open:
                open();
                break;
            case locked:
                locked();
                break;
        }
    }


    private void closed() {
        servoControl.setServoPos(ServoConstants.PTOLeft, OuttakeConstants.ptoLeftMinPos);
        servoControl.setServoPos(ServoConstants.PTORight, OuttakeConstants.ptoRightMaxPos);
    }

    private void open() {
        servoControl.setServoPos(ServoConstants.PTOLeft, OuttakeConstants.ptoLeftMidPos);
        servoControl.setServoPos(ServoConstants.PTORight, OuttakeConstants.ptoRightMidPos);
    }

    private void locked() {
        servoControl.setServoPos(ServoConstants.PTOLeft, OuttakeConstants.ptoLeftMaxPos);
        servoControl.setServoPos(ServoConstants.PTORight, OuttakeConstants.ptoRightMinPos);
    }
}
