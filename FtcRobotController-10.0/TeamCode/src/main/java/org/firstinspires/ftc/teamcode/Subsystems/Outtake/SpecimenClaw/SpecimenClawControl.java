package org.firstinspires.ftc.teamcode.Subsystems.Outtake.SpecimenClaw;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class SpecimenClawControl {
    private final ServoControl servoControl;
    private SpecimenClawStates prevSpecimenClawState;

    public SpecimenClawControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevSpecimenClawState != OuttakeStates.getSpecimenClawState()) {
            updateStates();
            prevSpecimenClawState = OuttakeStates.getSpecimenClawState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getSpecimenClawState()) {
            case closed:
                servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMinPos);
                break;
            case open:
                servoControl.setServoPos(ServoConstants.release, OuttakeConstants.releaseServoMaxPos);
                break;
        }
    }
}
