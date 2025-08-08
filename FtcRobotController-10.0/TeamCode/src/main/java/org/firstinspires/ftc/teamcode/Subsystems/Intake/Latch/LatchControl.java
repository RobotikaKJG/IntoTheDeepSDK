package org.firstinspires.ftc.teamcode.Subsystems.Intake.Latch;

import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Motor.MotorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Sensor.SensorControl;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;

public class LatchControl {
    private final ServoControl servoControl;
    private final SensorControl sensorControl;
    private LatchStates prevLatchStates = LatchStates.closed;

    public LatchControl(ServoControl servoControl, SensorControl sensorControl) {
        this.servoControl = servoControl;
        this.sensorControl = sensorControl;
    }

    public void update() {
        if(IntakeStates.getLatchState() != prevLatchStates) {
            updateStates();
            prevLatchStates = IntakeStates.getLatchState();
        }
        if(IntakeStates.getLatchState() == LatchStates.closed) {
            if (wrongColor()) {
                IntakeStates.setLatchState(LatchStates.open);
                updateStates();
            }
        }
        else if(correctColor()) {
            IntakeStates.setLatchState(LatchStates.closed);
            updateStates();
        }
    }

    public void updateStates() {
        switch (IntakeStates.getLatchState()) {
            case closed:
                servoControl.setServoPos(ServoConstants.intake, IntakeConstants.intakeServoMinPos);
                break;
            case open:
                servoControl.setServoPos(ServoConstants.intake, IntakeConstants.intakeServoMaxPos);
                break;
        }

    }

    private boolean wrongColor(){
        return sensorControl.isOtherAllianceColor() || sensorControl.isYellow();
    }

    private boolean correctColor() {
        return sensorControl.isAllianceColor();
    }
}

