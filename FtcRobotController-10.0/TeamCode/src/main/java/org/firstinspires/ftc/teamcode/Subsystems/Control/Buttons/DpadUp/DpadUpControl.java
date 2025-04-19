package org.firstinspires.ftc.teamcode.Subsystems.Control.Buttons.DpadUp;

import org.firstinspires.ftc.teamcode.Subsystems.Control.ButtonStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Intake.Motor.IntakeMotorStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.SampleClaw.SampleClawStates;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.Slides.VerticalSlideStates;

public class DpadUpControl {

    public void update() {
        switch (ButtonStates.getDpadUpState()) {
            case toggleMotor:
                toggleMotor();
                break;
            case slideStepUp:
                slideStepUp();
                break;
            case idle:
                break;
        }
    }

    private void toggleMotor() {
        switch(IntakeStates.getMotorState()){
            case forward:
                IntakeStates.setMotorState(IntakeMotorStates.backward);
                break;
            case backward:
                IntakeStates.setMotorState(IntakeMotorStates.forward);
                break;
            case idleWasForward:
                IntakeStates.setMotorState(IntakeMotorStates.idleWasBackward);
                break;
            case idleWasBackward:
                IntakeStates.setMotorState(IntakeMotorStates.idleWasForward);
                break;
        }
    }

    private void slideStepUp(){
        if(OuttakeStates.getSampleClawState() == SampleClawStates.closed)
            OuttakeStates.setSampleClawState(SampleClawStates.freeMove);
        OuttakeStates.setVerticalSlideState(VerticalSlideStates.stepUp);
    }
}
