package org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo;

import org.firstinspires.ftc.teamcode.HardwareInterface.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class EjectionServoControl {
    private EjectionServoStates prevEjectionServoState = EjectionServoStates.closed;
    private final ServoControl servoControl;

    public EjectionServoControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update(){
        if(IntakeStates.getEjectionServoState() != prevEjectionServoState) {
            updateStates();
            prevEjectionServoState = IntakeStates.getEjectionServoState();
        }
    }

    private void updateStates() {
        switch (IntakeStates.getEjectionServoState()){
            case closed:
                servoControl.setServoPos(ServoConstants.intake, IntakeConstants.intakeServoMinPos);
                break;
            case open:
                servoControl.setServoPos(ServoConstants.intake, IntakeConstants.intakeServoMaxPos);
                break;
        }
    }
}
