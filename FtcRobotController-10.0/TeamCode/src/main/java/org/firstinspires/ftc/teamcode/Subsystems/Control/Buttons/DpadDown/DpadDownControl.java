package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadDown;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
//import org.firstinspires.ftc.teamcode.Subsystems.Intake.EjectionServo.EjectionServoStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleLock.SampleLockStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class DpadDownControl {

    public void update() {
        switch (ButtonStates.getDpadDownState()) {
            case toggleEjectionServo:
                toggleEjectionServo();
                break;
            case slideStepDown:
                slideStepDown();
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

    private void slideStepDown(){
        if(OuttakeStates.getSampleClawState() == SampleClawStates.closed)
            OuttakeStates.setSampleClawState(SampleClawStates.freeMove);
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.stepDown);
    }
}
