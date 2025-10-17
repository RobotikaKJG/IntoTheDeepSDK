package org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class LatchControl {
    private LatchStates prevLatchState;
    private final ServoControl servoControl;

    public LatchControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update(){
        if(IntakeStates.getLatchState() != prevLatchState) {
            updateStates();
            prevLatchState = IntakeStates.getLatchState();
        }
    }

    private void updateStates() {
        switch (IntakeStates.getLatchState()){
            case closed:
                servoControl.setServoPos(ServoConstants.lock, IntakeConstants.lockServoMaxPos);
                break;
            case middle:
                servoControl.setServoPos(ServoConstants.lock, IntakeConstants.lockServoMiddlePos);
                break;
            case open:
                servoControl.setServoPos(ServoConstants.lock, IntakeConstants.lockServoMinPos);
                break;
        }
    }
}
