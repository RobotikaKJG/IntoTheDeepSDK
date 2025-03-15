package org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;

public class EjectionServoControl {
    private SampleLockStates prevEjectionServoState = SampleLockStates.closed;
    private final ServoControl servoControl;

    public EjectionServoControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update(){
        if(OuttakeStates.getSampleLockState() != prevEjectionServoState) {
            updateStates();
            prevEjectionServoState = OuttakeStates.getSampleLockState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getSampleLockState()){
            case closed:
                servoControl.setServoPos(ServoConstants.lock, OuttakeConstants.lockServoMinPos);
                break;
            case open:
                servoControl.setServoPos(ServoConstants.lock, OuttakeConstants.lockServoMaxPos);
                break;
        }
    }
}
