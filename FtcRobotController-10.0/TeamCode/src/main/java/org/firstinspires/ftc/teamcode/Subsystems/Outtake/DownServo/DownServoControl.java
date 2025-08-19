package org.firstinspires.ftc.teamcode.Subsystems.Outtake.DownServo;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class DownServoControl {
    private final ServoControl servoControl;
    private DownServoStates prevDownServoState;

    public DownServoControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevDownServoState != OuttakeStates.getDownServoState()) {
            updateStates();
            prevDownServoState = OuttakeStates.getDownServoState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getDownServoState()) {
            case active:
                servoControl.setServoPos(ServoConstants.outtakeDown, OuttakeConstants.outtakeDownServoMaxPos);
                break;
            case down:
                servoControl.setServoPos(ServoConstants.outtakeDown, OuttakeConstants.outtakeDownServoMinPos);
                break;
        }
    }
}
