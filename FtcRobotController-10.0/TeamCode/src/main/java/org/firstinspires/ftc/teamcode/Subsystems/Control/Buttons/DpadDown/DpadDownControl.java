package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadDown;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ControlStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class DpadDownControl {

    public void update() {
        switch (ControlStates.getDpadDownState()) {
            case toggleEjectionServo:
                toggleEjectionServo();
                break;
            case idle:
                break;
        }
    }

    private void toggleEjectionServo() {
        if(IntakeStates.getEjectionServoState() == EjectionServoStates.closed)
            IntakeStates.setEjectionServoState(EjectionServoStates.open);
        else
            IntakeStates.setEjectionServoState(EjectionServoStates.closed);
    }
}
