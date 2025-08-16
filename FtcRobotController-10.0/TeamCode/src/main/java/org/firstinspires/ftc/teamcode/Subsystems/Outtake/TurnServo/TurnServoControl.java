package org.firstinspires.ftc.teamcode.Subsystems.Outtake.TurnServo;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class TurnServoControl {
    private TurnServoStates prevTurnServoState;
    private ServoControl servoControl;

    public TurnServoControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevTurnServoState != OuttakeStates.getTurnServoState()) {
            updateStates();
            prevTurnServoState = OuttakeStates.getTurnServoState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getTurnServoState()) {
            case takePos:
                servoControl.setServoPos(ServoConstants.outtakeClawTurn, OuttakeConstants.outtakeClawTurnServoMinPos);
                break;
            case placePos:
                servoControl.setServoPos(ServoConstants.outtakeClawTurn, OuttakeConstants.outtakeClawTurnServoMaxPos);
                break;
            case idle:
                break;
        }
    }
}
