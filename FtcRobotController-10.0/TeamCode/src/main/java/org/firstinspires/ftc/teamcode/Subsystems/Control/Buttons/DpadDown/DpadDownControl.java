package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadDown;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
//import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;

public class DpadDownControl {

    public void update() {
        switch (ButtonStates.getDpadDownState()) {
            case toggleEjectionServo:
                toggleEjectionServo();
                break;
            case toggleClawFreeMove:
                toggleClawFreeMove();
                break;
            case idle:
                break;
        }
    }

    private void toggleEjectionServo() {
        if(OuttakeStates.getSampleLockState() == SampleLockStates.closed)
            OuttakeStates.setSampleLockState(SampleLockStates.open);
        else
            OuttakeStates.setSampleLockState(SampleLockStates.closed);
    }

    private void toggleClawFreeMove(){
        if(OuttakeStates.getSampleClawState() == SampleClawStates.closed)
            OuttakeStates.setSampleClawState(SampleClawStates.freeMove);
        else if(OuttakeStates.getSampleClawState() == SampleClawStates.freeMove)
            OuttakeStates.setSampleClawState(SampleClawStates.closed);
    }
}
