package org.firstinspires.ftc.teamcode.Subsystems.Intake.Pivot;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class PivotControl {
    private final ServoControl servoControl;
    private PivotStates prevPivotState;

    public PivotControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevPivotState != IntakeStates.getPivotState()) {
            updateStates();
            prevPivotState = IntakeStates.getPivotState();
        }
    }

    private void updateStates() {
        switch (IntakeStates.getPivotState()) {
            case up:
                servoControl.setServoPos(ServoConstants.intake, IntakeConstants.intakeServoMaxPos);
                break;
            case overSub:
                servoControl.setServoPos(ServoConstants.intake, IntakeConstants.intakeServoSubPos);
                break;
            case down:
                servoControl.setServoPos(ServoConstants.intake, IntakeConstants.intakeServoMinPos);
                break;
        }
    }
}
